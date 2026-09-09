package com.telemetry.indyTelemetry.persistence.entity;

import com.telemetry.indyTelemetry.domain.Area;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asset_event_area1")
public class EventArea1Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_area1_seq")
    @SequenceGenerator(name = "event_area1_seq", sequenceName = "event_area1_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Instant time;
    @Column(name = "asset_name")
    private String assetName;
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "operational_status")
    private Boolean operationalStatus;

    private Float tag1;
    private Float tag2;
    private Float tag3;
    private Float tag4;
    private Float tag5;


}
