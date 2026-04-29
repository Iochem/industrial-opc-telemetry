# Industrial OPC Telemetry #

**Status**: In development

## Functionalities

- OPC UA client connection using Eclipse Milo.
- Real-time data collection from industrial devices.
- Subscription-based telemetry monitoring.
- operational state (on/off) tracking.
- Structured telemetry processing.
- Modular configuration for multiple assets.

##  Notes

- All endpoints and NodeIds in this project are simulated.
  - Example: opc.tcp://localhost:4840
  kd=99;s=Demo/On
- No real industrial data is exposed.
- The focus is on architecture, OPC UA integration and real-time processing.


##  Technologies
- Java 17
- Spring Boot
- JPA / Hibernate
- OPC UA (Eclipse Milo)
- PostgreSQL / TimescaleDB (planned)
- Lombok


## How to use



```bash
## 1. Clone the repository
https://github.com/Iochem/industrial-opc-telemetry.git

## 2. Configuration
- The project uses a simulated OPC UA setup by default:
opc.tcp://localhost:4840
- To connect to a real server, update the endpoint and NodeIds.

## 3. Expected behavior
The application will:
- Connect to the OPC UA server
- Monitor operational state
- Subscribe to telemetry signals
````
Developed by [Brenda Nunes] (https://github.com/Iochem)