package com.telemetry.indyTelemetry.infrastructure.apacheKafka.event;

import com.telemetry.indyTelemetry.domain.Area;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetEventArea1 {
    // fixed camps — All asset have
    private String assetName;
    private Area area;
    private Instant timestamp;
    private Boolean operationalStatus;
    //private String connectionStatus;
    private Float tag01;
    private Float tag02;
    private Float tag03;
    private Float tag04;
    private Float tag05;
}
