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
    private String operationalStatus;
    //private String connectionStatus;

    // Optional camps — Only some asset have
    private Map<String, String> generalTelemetryEvent;
}
