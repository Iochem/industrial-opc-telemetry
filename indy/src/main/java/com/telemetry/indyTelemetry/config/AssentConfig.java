package com.telemetry.indyTelemetry.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AssentConfig {
    private String assentName;
    private String area;
    private String endpoint;
    private final String operationalStatus;
    private Map<String, String> generalTelemetry;
}

