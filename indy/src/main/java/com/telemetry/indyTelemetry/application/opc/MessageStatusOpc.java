package com.telemetry.indyTelemetry.application.opc;


import org.springframework.stereotype.Component;

@Component
public class MessageStatusOpc {
    public String createMessageStatusOpc(int status0) {
        if (status0 == 0) return "Stable connection";
        else return "connection failure";
    }
}
