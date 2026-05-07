package com.telemetry.indyTelemetry.opc;

import com.telemetry.indyTelemetry.config.AssetConfig;
import lombok.RequiredArgsConstructor;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class OpcTelemetrySubscriber {
    private static final Logger log = LoggerFactory.getLogger(OpcTelemetrySubscriber.class);
    private final AssetConfig config;
    private final OpcUaClient client;



    public void start() throws Exception {
        startStatusMonitor();
        startMonitorSubscription();
    }

    private void startStatusMonitor(){
        log.info("Hearbet started for: {} ", config.getAssetName());

    }

    private void startMonitorSubscription(){
        log.info("Subscription started for: {} ", config.getAssetName());

    }

}
