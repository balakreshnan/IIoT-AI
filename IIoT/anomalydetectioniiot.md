# Anomaly Detection in Industrial IoT using Azure PaaS IoT Services.

## Use Cases

How to build anomaly detection in industrial internet of things using Azure PaaS. Anomaly detection is something every manufacturing company implements.

For Example: Temperature going above threshold. 

The below are some few different Anomaly detection architectures based from easy to more advanced.

## Architectures

Low Code No Code options 1

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/Anomaly-IIoT-1.jpg "Architecture")

Components:

- Iot Hub
- Raw Event - is a consumer group with a copy of the raw data.
- Time Series Insights - For Root Cause Analysis and Long term Storage with Hot and Cold Storage.
- Stream Analytics - Pull Events to detect Anomaly detection
    https://docs.microsoft.com/en-us/azure/stream-analytics/stream-analytics-machine-learning-anomaly-detection
- Event Hub - To get the alert to send
- Flow to get the event from Azure Event hub and using office 365 email to send emails Alerts.
    https://docs.microsoft.com/en-us/azure/connectors/connectors-create-api-azure-event-hubs

Low Code No Code Option 2

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/Anomaly-IIoT-2.jpg "Architecture")

- Iot Hub
- Raw Event - is a consumer group with a copy of the raw data.
- Time Series Insights - For Root Cause Analysis and Long term Storage with Hot and Cold Storage.
- Stream Analytics - Pull Events to detect Anomaly detection
    https://docs.microsoft.com/en-us/azure/stream-analytics/stream-analytics-machine-learning-anomaly-detection
- Event Hub - To get the alert to send
- Logic App to get the event from Azure Event hub and using office 365 email to send emails Alerts.
    https://docs.microsoft.com/en-us/azure/connectors/connectors-create-api-azure-event-hubs

Low Code Option 1

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/Anomaly-IIoT-3.jpg "Architecture")

- Iot Hub
- Raw Event - is a consumer group with a copy of the raw data.
- Azure data explorer - For Root Cause Analysis and Long term Storage with Hot and Cold Storage. Also Machine learning and anomaly detection and basketing options with more 200 or more functions to run adhoc queires and process large data set.
- Stream Analytics - Pull Events to detect Anomaly detection
    https://docs.microsoft.com/en-us/azure/stream-analytics/stream-analytics-machine-learning-anomaly-detection
- Event Hub - To get the alert to send
- Flow to get the event from Azure Event hub and using office 365 email to send emails Alerts.
    https://docs.microsoft.com/en-us/azure/connectors/connectors-create-api-azure-event-hubs

Low Code Option 2

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/Anomaly-IIoT-4.jpg "Architecture")

- Iot Hub
- Raw Event - is a consumer group with a copy of the raw data.
- Azure data explorer - For Root Cause Analysis and Long term Storage with Hot and Cold Storage. Also Machine learning and anomaly detection and basketing options with more 200 or more functions to run adhoc queires and process large data set.
- Stream Analytics - Pull Events to detect Anomaly detection
    https://docs.microsoft.com/en-us/azure/stream-analytics/stream-analytics-machine-learning-anomaly-detection
- Event Hub - To get the alert to send
- Logic App to get the event from Azure Event hub and using office 365 email to send emails Alerts.
    https://docs.microsoft.com/en-us/azure/connectors/connectors-create-api-azure-event-hubs

Low Code Option 3

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/Anomaly-IIoT-5.jpg "Architecture")

- Iot Hub
- Raw Event - is a consumer group with a copy of the raw data.
- Azure data explorer - For Root Cause Analysis and Long term Storage with Hot and Cold Storage. Also Machine learning and anomaly detection and basketing options with more 200 or more functions to run adhoc queires and process large data set.
- Stream Analytics - Pull Events to detect Anomaly detection
    https://docs.microsoft.com/en-us/azure/stream-analytics/stream-analytics-machine-learning-anomaly-detection
- Event Hub - To get the alert to send
- Azure Function to get the event from Azure Event hub and using using third party email like Send Grid to email Alerts.
    https://docs.microsoft.com/en-us/azure/connectors/connectors-create-api-azure-event-hubs

End