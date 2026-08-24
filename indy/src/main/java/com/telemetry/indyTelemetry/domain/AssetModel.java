package com.telemetry.indyTelemetry.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AssetModel {
    private String assetName;
    private Area area;
    private String endpoint;
    private String operationalStatus;
    private String operationalTagStatus;
    private Map<String, String> generalTelemetry;
}

