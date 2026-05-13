package com.telemetry.indyTelemetry.infrastructure.opc;

import com.telemetry.indyTelemetry.domain.AssetModel;
import lombok.RequiredArgsConstructor;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class OpcTelemetrySubscriber {
    private final AssetModel config;
    private final OpcUaClient client;

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

    private void startMonitorSubscription(){
        log.info("Subscription started for: {} ", config.getAssetName());

    }

}
