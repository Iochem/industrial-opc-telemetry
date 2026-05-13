package com.telemetry.indyTelemetry.infrastructure.opc;


import com.telemetry.indyTelemetry.config.AssetConfigFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpcClientStarter { //To start the client OPC

    private final AssetConfigFactory machineConfigFactory;

    @PostConstruct
    public void start(){
        machineConfigFactory.createAssents().forEach( assent -> {
            new OpcClient(assent).start();
        });

    }

}
