# Industrial OPC Telemetry

**Status**: In development

## Functionalities

- `OPC UA` client connection using `Eclipse Milo`
- Real-time data collection from industrial devices
- Subscription-based telemetry monitoring (`no polling-only`)
- Operational state (`on/off`) tracking
- Automatic reconnection handling
- Structured event logging
- Modular configuration for multiple assets

**Architecture**: OPC UA Server --> OPC Client --> Telemetry Subscriber --> Real-time Processing

## Notes

- All endpoints and NodeIds in this project are simulated  
  Example: `<ENDPOINT>`  
  `<NODE_ID_02>`
- No real industrial data is exposed
- Focus on OPC UA integration and real-time processing architecture

## Kafka Message Example

The telemetry data is published to Kafka using a structured JSON message:

```json
{
  "assetName": "asset1",
  "area": "AREA1",
  "timestamp": "2026-08-12T08:25:07.841-03:00",
  "operationalStatus": true,
  "tag01": 95.51431,
  "tag02": 45.57942,
  "tag03": 19.0,
  "tag04": 12.179879,
  "tag05": 9.5161862
}
```


## Technologies

- Java 21
- Spring Boot
- OPC UA (Eclipse Milo)
- Apache Kafka
- PostgreSQL / TimescaleDB (planned)
- grafana (planned)
- Lombok


## How to use

```bash
## 1. Clone the repository
https://github.com/Iochem/industrial-opc-telemetry.git

## 2. Configuration
- The project uses a simulated OPC UA setup by default:
<ENDPOINT>
- To connect to a real server, update the endpoint and NodeIds.

## 3. Expected behavior
The application will:
- Connect to the OPC UA server
- Monitor operational state
- Subscribe to telemetry signals
````
Developed by [Brenda Nunes] (https://github.com/Iochem)