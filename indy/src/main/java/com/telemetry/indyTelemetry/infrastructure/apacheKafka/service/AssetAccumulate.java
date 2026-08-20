package com.telemetry.indyTelemetry.infrastructure.apacheKafka.service;

import com.telemetry.indyTelemetry.domain.Area;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssetAccumulate {

    public void area1Accumulate(String assetName, Map<String, Object> state, Area area, Object raw,  String tagName){

        if(area != null ) state.put("area", area);

        if(tagName.contains("_tag01")) {
            if(raw instanceof Number number) state.put("tag01", number.floatValue());
        }

        if(tagName.contains("_tag02")) {
            if(raw instanceof Number number) state.put("tag02", number.floatValue());
        }
        if(tagName.contains("_tag03")) {
            if(raw instanceof Number number) state.put("tag03", number.floatValue());
        }
        if(tagName.contains("_tag04")) {
            if(raw instanceof Number number) state.put("tag04", number.floatValue());
        }
        if(tagName.contains("_tag05")) {
            if(raw instanceof Number number) state.put("tag05", number.floatValue());
        }

    }
}
