package com.telemetry.indyTelemetry.infrastructure.opc;

import com.telemetry.indyTelemetry.domain.AssetModel;
import lombok.RequiredArgsConstructor;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RequiredArgsConstructor
public class OpcTelemetrySubscriber {
    private final AssetModel config;
    private final OpcUaClient client;

    private UaSubscription subscription;
    private ScheduledFuture<?> statusMonitorFuture;
    private static final Logger log = LoggerFactory.getLogger(OpcTelemetrySubscriber.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    //Constants
    private static final double TELEMETRY_INTERVAL_MS = 600.0;
    private static final int TELEMETRY_QUEUE_SIZE = 15;
    private static final int STATE_INTERVAL = 5;


    public void start() throws Exception {
        startStatusMonitor();
        startMonitorSubscription();
    }

    /**
     * Starts periodic OPC UA polling for the asset
     *
     */
    private void startStatusMonitor(){
        log.info("Hearbet started for: {} ", config.getAssetName());

        NodeId statusNode = NodeId.parse(config.getOperationalStatus());

        statusMonitorFuture = scheduler.scheduleWithFixedDelay(() -> {
            try {
                DataValue value = client.readValue(
                        0,
                        TimestampsToReturn.Neither,
                        statusNode
                ).get(3, TimeUnit.SECONDS);

                boolean operational = Boolean.TRUE.equals(value.getValue().getValue());

                log.info("event=asset_state asset={} operational={}",
                        config.getAssetName(),
                        operational
                );

            } catch (Exception e) {
                log.warn("Status monitor failed for asset={}", config.getAssetName());
            }

        }, 0, STATE_INTERVAL, TimeUnit.SECONDS);

    }


    /**
     * Starts OPC UA telemetry monitoring by creating subscriptions
     * Registers monitored items for configured asset tags.
     */
    private void startMonitorSubscription() throws ExecutionException, InterruptedException {
        // === Create subscription Opc Ua ===
        subscription = client.getSubscriptionManager()
                .createSubscription(500.0)
                .get();

        int handle = 1;

        for (Map.Entry<String, String> telemetry : config.getGeneralTelemetry().entrySet()) {

            NodeId nodeId = NodeId.parse(telemetry.getKey());

            // === Define what will be to monitored ===
            ReadValueId readValueId = new ReadValueId(
                    nodeId,
                    AttributeId.Value.uid(),
                    null,
                    QualifiedName.NULL_VALUE
            );

            // === Config how this tag will be monitored ===
            MonitoringParameters parameters = new MonitoringParameters(
                    Unsigned.uint(handle++),
                    TELEMETRY_INTERVAL_MS,
                    null,
                    Unsigned.uint(TELEMETRY_QUEUE_SIZE),
                    true
            );

            // === Build monitoring item request ===
            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                    readValueId,
                    MonitoringMode.Reporting,
                    parameters
            );

            // === Register monitored item in the subscription ===
            // Here introduces the tag into subscription
            subscription.createMonitoredItems(
                    TimestampsToReturn.Both,
                    List.of(request),
                    (item, id) -> item.setValueConsumer((monitoredItem, value) -> {

                        if (value == null || !value.getStatusCode().isGood())  return;

                        Object raw = value.getValue().getValue();

                        log.info("event=telemetry_update asset={} signal={} value={}", config.getAssetName(), telemetry.getValue(), raw
                        );
                    })
            ).get();
        }

    }

}
