package com.telemetry.indyTelemetry.infrastructure.configTags;

import com.telemetry.indyTelemetry.domain.AssetModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class AssetConfigFactory {

    public List<AssetModel> createAssets() {
        return Stream.of(
                PlantCity1.get()

        )
                .flatMap(List::stream)
                .toList();
    }
}