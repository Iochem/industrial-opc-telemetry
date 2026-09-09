package com.telemetry.indyTelemetry.persistence.repository;

import com.telemetry.indyTelemetry.persistence.entity.EventArea1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventArea1Repository extends JpaRepository<EventArea1Entity, Long> {
}
