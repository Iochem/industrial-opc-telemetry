package com.telemetry.indyTelemetry.persistence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemetry.indyTelemetry.persistence.repository.EventArea1Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventArea1Consumer {
    private final ObjectMapper objectMapper;
    private final EventArea1Repository repository;
}
