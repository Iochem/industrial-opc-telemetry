package com.telemetry.indyTelemetry.infrastructure.opc;

import com.telemetry.indyTelemetry.domain.AssetModel;
import lombok.RequiredArgsConstructor;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class OpcClient { // Handles operational state polling and telemetry subscriptions

    private final AssetModel config;
    private OpcUaClient client;
    private OpcTelemetrySubscriber subscriber;
    //private volatile boolean sessionActive = false;
    private static final Logger log = LoggerFactory.getLogger(OpcClient.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public void start() {
        scheduler.scheduleWithFixedDelay(this::ensureSession, 0, 10, TimeUnit.SECONDS);
    }

    private synchronized void ensureSession() {

        try{
            client = client.create(config.getEndpoint()); // create
            client.connect().get(15, TimeUnit.SECONDS);

            subscriber = new OpcTelemetrySubscriber(config, client);
            subscriber.start();

        } catch (Exception e) {
            log.error("OPC UA session unavailable for asset: {} | {}", config.getAssetName(), e.getMessage(), e);
        }
    }

}

