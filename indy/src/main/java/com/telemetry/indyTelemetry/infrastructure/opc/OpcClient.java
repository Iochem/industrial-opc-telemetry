package com.telemetry.indyTelemetry.infrastructure.opc;

import com.telemetry.indyTelemetry.domain.AssetModel;
import com.telemetry.indyTelemetry.infrastructure.apacheKafka.service.AssetProducer;
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
    private volatile boolean sessionActive = false;
    private static final Logger log = LoggerFactory.getLogger(OpcClient.class);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final AssetProducer producer;

    private int statusOpc = 0;

    public void start() {
        scheduler.scheduleWithFixedDelay(this::ensureSession, 0, 10, TimeUnit.SECONDS);
    }

    private synchronized void ensureSession() {

        if (sessionActive && client != null && subscriber != null && subscriber.isActive()) {
            return;
        }

        try {
            client = OpcUaClient.create(config.getEndpoint());
            client.connect().get(15, TimeUnit.SECONDS);

            subscriber = new OpcTelemetrySubscriber(config, client, producer);
            subscriber.statusOpcValidate(statusOpc);
            subscriber.start();
            sessionActive = true;
            statusOpc = 0;
           log.info("OPC UA connected asset={}", config.getAssetName());

        } catch (Exception e) {
            sessionActive = false;
            statusOpc = 1;
            subscriber.statusOpcValidate(statusOpc);
            producer.accumulatEventArea1(config.getAssetName(), config.getArea(), null, "", statusOpc);
            producer.createEventArea1(config.getAssetName(), false);

            log.error("OPC UA session unavailable for asset={} | {}", config.getAssetName(), e.getMessage(), e);
        }
    }
}


