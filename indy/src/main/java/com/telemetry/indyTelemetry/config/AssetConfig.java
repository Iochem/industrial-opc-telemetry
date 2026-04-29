package com.telemetry.indyTelemetry.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AssetConfig {
    private String assetName;
    private String area;
    private String endpoint;
    private final String operationalStatus;
    private Map<String, String> generalTelemetry;
}

