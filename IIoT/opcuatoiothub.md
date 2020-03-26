# How Collect data from OPC UA device and Send to IoT Hub

## Authors

Priya Aswani </br>
Balamurugan Balakreshnan

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcuasimualation1.jpg "Architecture")

## Use Case

Connect to industrial Devices using OPC UA and subscribe for tags to collect data and push to Iot Hub for further processing

Business Use case:

- Root Cause Analysis
- Predictive Maintenance
- Reliability
- Quality
- OEE
- Anomaly Detection
- Production Troubleshooting

Technical Use case:

- Collecting data for time series like historian using azure data explorer
- Root cause analytis and Trending using Azure Time Series Insights
- OPC UA based data collection for Industry X.0
- OPC DA to UA using tuneller or conversion tools.
- OEE using custom queries in Azure Time Series Insights
- Anomalies can be detected using trending and patterns using Azure Time Series Insights.
- Production troubleshooting for technicians to fix line issues or equipment failures.
- Custom pattern or adhoc manual anomaly and machine learning in sensor data using Azure data explorer.


## Steps

- Download Integrtionobjects OPC UA simulator
- Download UA experts UA Explorer
- Download the Azure Iot OPC publisher for UA
- Configure the publishednodes.json
- Configure IoT device and hub configuration in visual studio code
- Configure the OPC UA simulation server endpoint (URL) and update in publishednodes.json
- Run the project and see if data is getting processed.

Simulator is free and will only run 48 hours at a time and stop.

## OPC UA Simualtor 

https://integrationobjects.com/sioth-opc/sioth-opc-unified-architecture/opc-ua-server-simulator/

note: if you choose windows server please use Run as adminstrator to run the simulator so no errors.

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcsimulator1.jpg "opc simulator")

## Download UAexpert OPC client

https://www.unified-automation.com/products/development-tools/uaexpert.html

Open the UZexpert and on the left menu right click Add Server in the top menu 

Right click and then select connect. 

Scorll down to Custom Discovery 
Double click to add a server and then use OPC UA URL

for example: opc.tcp://localhost:62652/IntegrationObjects/UAServerSimulator

Then go to Projects -> Servers -> Add Right click connect and select above configured OPC server

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcviewer.jpg "opc explorer")


## OPC UA publisher Code

https://github.com/Azure/iot-edge-opc-publisher/tree/master

Open the Folder for opcpublisher in Visual Studio Code.

Make sure you still Azure IoT Tools in visual Studio.

Navigate to publishednodes.json file and replace the below. 

Assumption here is picking Random.Int1 and Randon.Boolean as tag names to send it to IoT hub. In real work this might change.

Code change for publishednodes.json

```
[
  {
    "EndpointUrl": "opc.tcp://localhost:62652/IntegrationObjects/UAServerSimulator",
    "UseSecurity": false,
    "OpcNodes": [
      {
        "Id": "ns=2;s=1:Dynamic?Random.Int1",
        "OpcSamplingInterval": 2000,
        "OpcPublishingInterval": 5000,
        "HeartbeatInterval": 3600,
        "SkipFirst": true
      },
      {
        "Id": "ns=2;s=1:Dynamic?Random.Boolean",
        "OpcSamplingInterval": 2000,
        "OpcPublishingInterval": 5000,
        "HeartbeatInterval": 3600,
        "SkipFirst": true
      }
    ]
  }
]
```

Create a iot hub and then create a iot device and get connection string.

Open the terminal and then run the project with the below settings.

dotnet run "opcuadevice" --deviceconnectionstring="HostName=xxxxxxx.azure-devices.net;DeviceId=opcdevice1;SharedAccessKey=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" --iotcentral --iothubprotocol=Http1 --autotrustservercerts=true --publishfile "C:\work\opc\iot-edge-opc-publisher-master\opcpublisher\publishednodes.json"

--iotcentral key word will make the JSON more readble format rather than complex structure.

Adjust the connection string and also the path for publishednodes.json.

Configure the visual studio code to monitor the iot hub. 

Sample output from above samples was 

```
[
  {
    "Random.Int1": "14"
  },
  {
    "Random.Boolean": "true"
  },
  {
    "Random.Int1": "14"
  },
  {
    "Random.Boolean": "true"
  }
]
```

Now time to move the data in to Azure data explorer to run some root cause and trend analysis.