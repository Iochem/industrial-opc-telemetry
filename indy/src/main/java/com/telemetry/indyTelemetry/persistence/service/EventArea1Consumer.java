package com.telemetry.indyTelemetry.persistence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemetry.indyTelemetry.infrastructure.apacheKafka.event.AssetEventArea1;
import com.telemetry.indyTelemetry.persistence.entity.EventArea1Entity;
import com.telemetry.indyTelemetry.persistence.repository.EventArea1Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventArea1Consumer {
    private final ObjectMapper objectMapper;
    private final EventArea1Repository repository;

    @KafkaListener(topics = "area01-events", groupId = "timescale-consumer")
    public void consumerArea1 (String json, Acknowledgment ack){

        try{
            AssetEventArea1 event = objectMapper.readValue(json, AssetEventArea1.class);

            EventArea1Entity entity = new EventArea1Entity();
            entity.setTime(event.getTimestamp());
            entity.setArea(event.getArea());
            entity.setAssetName(event.getAssetName());
            entity.setOperationalStatus(event.getOperationalStatus());
            entity.setTag1(event.getTag01());
            entity.setTag2(event.getTag02());
            entity.setTag3(event.getTag03());
            entity.setTag4(event.getTag04());
            entity.setTag5(event.getTag05());
            repository.saveAndFlush(entity);

        } catch (Exception e) {
            log.error("Error processing event", e);
        }
    }

    private boolean isDatabaseError(Exception e){
        Throwable current = e;

        while(current != null){
            if (current instanceof org.springframework.dao.DataAccessException
                    || current instanceof java.sql.SQLException
                    || current instanceof jakarta.persistence.PersistenceException
                    || current instanceof org.hibernate.exception.JDBCConnectionException) {
                return true;
            }
            current = current.getCause();

        }
        return false;
    }

}
