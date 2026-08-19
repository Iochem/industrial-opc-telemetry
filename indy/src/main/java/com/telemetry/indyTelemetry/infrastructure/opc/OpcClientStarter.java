package com.telemetry.indyTelemetry.infrastructure.opc;



import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpcClientStarter { //To start the client OPC

    private final com.telemetry.indyTelemetry.infrastructure.configTags.AssetConfigFactory machineConfigFactory;

    @PostConstruct
    public void start(){
        machineConfigFactory.createAssets().forEach( assent -> {
            new OpcClient(assent).start();
        });

    }

}
