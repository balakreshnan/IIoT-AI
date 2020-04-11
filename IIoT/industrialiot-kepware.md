# Industrial IoT Reference Architecture - Kepware

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/IndustrialIoT-kepware.jpg "Architecture")

## Architecture Explained

- Intelligent Edge - Edge Data collection and data management
- IoT Hub - Gateway to Cloud with Security
- Processing Path 
  - Anomaly and Rules Path
  - Cloud Historian Path - For OEE/Remote Monitoring/Predictive Maintenance/Dashboard/Operations
  - Advanced Analytics Path - For Business reporting across multiple locations/plants/factories/across countries
  - Machine learning Path - for Predictive maintenance/Forecasting and other ML use cases
  - Integrate with Business Apps Path - to send and recevive data between business systems
- Common Services
  - DevOps - Github for code repo where available and CI/CD where available
  - Azure AD - for authentication
  - Key Vault - Storing secrets
  - Monitor using Azure Monitor and Application Insights
  - Security using Sentinel
- BackUp
- DR
- Scale
- Long Term and short term storage
- Dashboard and Visualization using Power BI, Custom Dashboard, Mobile, Chat bot, Voice interfaces
- Integrate with other business systems
- Security
  - Firewall
  - Private VNet
  - Encryption at Rest
  - Encryption at Transit
  - Encryption on Data for PII and other security compliance
  - Data Security for Authorization
- PaaS
- Data Governance
- Master data management
- Real Time processing

The above architecture looks at manufacturing as a whole and enabling them to build their next generation connected factory using Platform as service concept

Biggest challenge is to bridge the gap between IT and OT systems.

OT systems are critical to run a manufacturing plant and also maintain them. OT systems are also heavily automated.

By building next generation IT/OT convergence system can enable traceability and also Enterprise wide view of manufacturing across the globe.

More details to come.
