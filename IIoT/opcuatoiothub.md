# Industrial Internet of Things (IIoT) Reference Architecture

Empowering manufacturer's to achieve more.

## Authors

Priya Aswani </br>
Balamurugan Balakreshnan

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcuasimualation1.jpg "Architecture")

## Use Case

To Enable Digital Transformation and ability to do AI/ML like Predictive Maintenance for manufacturing operations first we need to collect data from sensor to know how it is performing. If we have sensor data we can find patterns or behaviour to do machine learning or deep learning and apply advanced capacbility to solve real problems. Industrial IoT becomes a enabler and also AI is becoming a part of Industrial IoT space for Industry 4.0. 

The below is not a solution for all up manufacturing operations or MOM (manufacturing operation management) but a enabler that makes it possible. But also provide a cloud native platform with scale as needed is the idea behind the reference architecture. 

If manufacturer's can collect data atleast they can manually do analysis by looking at sensor data or charting or trending to see patterns and troubleshoot issues and find better solutions. The below option empowers manufacturer including the plant folks to take data driven decisions and know what's going on in their plants.

This is also one of the solution to above use case built on cloud native platform as a service and also made simple and easy to consume and wasy to operate considering other solutions in the market place. The below solution is also for manufacturer to build or have a partner built it for them. But it is much easier to build than building from scratch.

So to provide a solution for the above use case with currently what is there in the technology space for manufacturing companies the below is one such reference architecture pattern.

Connect to industrial Devices using OPC UA/DA and subscribe for tags to collect data and push to Iot Hub for further processing

Business Use case:

- Root Cause Analysis
- Predictive Maintenance
- Reliability
- Quality
- OEE
- Anomaly Detection
- Production Troubleshooting
- Condition based monitoring is Explained later in the document. Look for condition based monitoring section.

Technical Use case:

- Collecting data for time series like historian using azure data explorer
- Root cause analysis and Trending using Azure Time Series Insights
- OPC UA based data collection for Industry X.0
- OPC DA to UA using tunneller or conversion tools.
- OEE using custom queries in Azure Time Series Insights
- Visual Anomalies can be detected using trending and patterns using Azure Time Series Insights.
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

## Output from Azure Time Series Insights:

Only selecting Sensor tags and adding as series to charts we get this output

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/tsioutputopc1.jpg "Architecture")

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/tsioutputopc2.jpg "Architecture")

The above sample pciture show stepped interpolation and min and max sliced by Boolean value.

Azure Time Series insights has great charting capability to analyze and troubleshoot issue in manufacturing environment.

But to go deep dive analysis in Time Series then we need to use Azure Data Explorer.

Opc Output inside in our preview Time series insights. As you can see we can plot sensor data and visualize the trends.

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/timeseries15.jpg "Architecture")

## Azure Data Explorer output:

Azure Data explorer allows us to do charting and trending by writing Kusto queries. Doesn't have the click and go environment. 

But Azure data explorer allow us to bring data from other business systems and then combine that seamlessly with time series data for queiring capability. So we can bring order data, Asset management data from Asset management system or ERP's and join them and build queiries.

Azure data explorer also allows us to expand and create our own dashboards using third party tools or custom web sites.

Azure Data explorer also has lot of time series functions already in built to Time series analysis like forecasting, Anomaly detection and even machine learning like auto clustering and basket analysis.

To get Azure data explorer start first create the schema to store the data

```
// Create table command
////////////////////////////////////////////////////////////
.create table ['opcdata1']  (['data']:dynamic)

// Create mapping command
////////////////////////////////////////////////////////////
.create table ['opcdata1'] ingestion json mapping 'opcdata1_mapping' '[{"column":"data","path":"$","datatype":"dynamic"}]'
```

Now create a ingestion data store and connect to iot hub and make sure create new consumer group in endpoint as adxinput.

COnfigure the ingestion to use that consumer group and also for table use opcdata1 and for format choose Multiline JSON and then for mapping use opcdata1_mapping.

Click Update and wait untill configuration are saved.

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/adx1.jpg "Architecture")

Code to parse the details

```
opcdata1
| extend  ingesttime = ingestion_time()
| extend d=parse_json(data)
| evaluate bag_unpack(d) 
```

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/adx2.jpg "Architecture")

Now time to Create a data model to format the sensor data to use if for further analysis.

Create new schema

```
// Create table command //////////////////////////////////////////////////////////// 
.create-merge table ['opcDataRaw'] (['data']:dynamic) 
 
.alter-merge table opcDataRaw policy retention softdelete = 0d
 
// Create mapping command //////////////////////////////////////////////////////////// 
.create-or-alter table ['opcDataRaw'] ingestion json mapping 'opcDataRaw_mapping' '[{"column":"data","path":"$"}]'
 
.create-merge table ['opcDataFlat'] (TimeStamp:datetime, Key:string, Value:string) 
 
.create-or-alter function TransformOPCData()
{
opcDataRaw
| extend Key=tostring(bag_keys(data).[0])
| extend Value = tostring(['data'].[Key])
| extend TimeStamp = ingestion_time()
| project TimeStamp, Key, Value
}


//Create update policy to bind the stabing table, function, and the destination table 
.alter table opcDataFlat policy update
@'[{"IsEnabled": true, "Source": "opcDataRaw", "Query": "TransformOPCData()", "IsTransactional": true, "PropagateIngestionProperties": true}]'
```

Now Change the injestion table to opcDataRaw and mapping to opcDataRaw_mappings and then save.

Now wait and see it the data is flowing.

```
opcDataFlat
| limit 2000
```

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/adx3.jpg "Architecture")

Now lets get one data point to filter for one sensor details

```
opcDataFlat
| where Key == "Random.Int1"
| project TimeStamp, Key, Value
| render timechart  
```

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/adx4.jpg "Architecture")

Now time to write kusto query to do other charts using render key word.

Trending with 1 minute interval

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/trend1.jpg "Trending")

Trending with 15 minute interval

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/trend2.jpg "Trending")

Trending with 1 hour minute interval

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/trend3.jpg "Trending")

Anomaly Detection

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/anomaly1.jpg "Trending")

To Store long term data we can do continous export out to blob or ADLS gen2 storage for long term access.

- Create a Storage Account ADLS Gen2
- Create a container called opcoutput
- Get the Storage account name, Key and also container name for Azure data explorer configuration
- Configure the Azure data explorer

in case if you have already create the table here is the drop table command

```
.drop external table opcExternalLongTerm
```

Now create the External Table to store for long term

```
.create external table opcExternalLongTerm (TimeStamp:datetime, Key:string, Value:string) 
kind=blob
partition by 
   "Key="Key,
   bin(TimeStamp, 1d)
dataformat=csv
( 
   h@'https://xxxxxx.blob.core.windows.net/opcoutput;xxxxxxxxxxxxxxxxxxxxxxxxx'
)
```

Now configure the continous export data in Azure Data Explorer

```
.create-or-alter continuous-export opccontinousexport
over (opcDataFlat)
to table opcExternalLongTerm
with
(intervalBetweenRuns=1h, 
 forcedLatency=10m, 
 sizeLimit=104857600)
<| opcDataFlat
```

To See the exports

```
.show continuous-export opccontinousexport exported-artifacts | where Timestamp > ago(1h)
```

To find failures please use this command

```
.show continuous-export opccontinousexport failures
```

To enable and diable use the below

```
.enable continuous-export opccontinousexport

.disable continuous-export opccontinousexport
```

To query the data please use:

```
external_table("opcExternalLongTerm") | take 100
```

## Condition Based Monitoring / Anomaly Detection

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcuasimualation2.jpg "Architecture")

To Enable Condition based monitoring we can use Stream Analytics. Stream analytics can watch the sensor data and based on condition defined as reference data set in blob storage can be leveraged to make it dynamics and adjustable rules can detect and trigger a rule and send that to Azure function which can further do more processing or send email using tools like SendGrid or other third Party or using custom code as well.

From the IoT hub create a consumer group alled sainput and now a copy of IoTHub messages are going to this consumer group.

Create and configure Azure stream analytics.

Set input as iot hub and connect to above consumer group called sainput

Set output as blob storage or another event hub to send alerts

Create a eventhub called anomalyevt and send the below queired data to event hub for further processing.

Write the query as below

```
WITH AnomalyDetectionStep AS
(
    SELECT
        EVENTENQUEUEDUTCTIME AS time,
        IoTHub.ConnectionDeviceId AS deviceid,
        EventProcessedUtcTime AS ProcessedTime,
        IotHub.EnqueuedTime AS IotHubTime,
        CAST([Random.Int1] AS float) AS temp,
        AnomalyDetection_SpikeAndDip(CAST([Random.Int1] AS float), 95, 120, 'spikesanddips')
            OVER(LIMIT DURATION(second, 120)) AS SpikeAndDipScores
    FROM inputopc where [Random.Int1] <> 'null'
)
SELECT
    time,
    temp,
    deviceid,
    ProcessedTime,
    IotHubTime,
    CAST(GetRecordPropertyValue(SpikeAndDipScores, 'Score') AS float) AS
    SpikeAndDipScore,
    CAST(GetRecordPropertyValue(SpikeAndDipScores, 'IsAnomaly') AS bigint) AS
    IsSpikeAndDipAnomaly
INTO anomalyputput
FROM AnomalyDetectionStep

SELECT
    time,
    temp,
    deviceid,
    ProcessedTime,
    IotHubTime,
    CAST(GetRecordPropertyValue(SpikeAndDipScores, 'Score') AS float) AS
    SpikeAndDipScore,
    CAST(GetRecordPropertyValue(SpikeAndDipScores, 'IsAnomaly') AS bigint) AS
    IsSpikeAndDipAnomaly
INTO outputanomaly
FROM AnomalyDetectionStep
```

Kepware OPC data coming iot hub

```
WITH Telemetry AS (
SELECT        
        GetMetadataPropertyValue(inputopc, '[IoTHUB].[ConnectionDeviceId]') as DeviceID,
        inputopc.EventEnqueuedUtcTime AS EntryTime,
        arrayElement.ArrayIndex AS AIndex, 
        arrayElement.ArrayValue AS AValue

FROM inputopc
CROSS APPLY GetArrayElements(inputopc.[values]) AS arrayElement  
), 
ArrayData AS (
SELECT 
       DeviceID,
       EntryTime,
       AIndex as ArrayIndex,
       AValue.id as ArrayID,
       AValue.v as ArrayValue,
       AValue.t as ArrayTime

FROM Telemetry
)
SELECT 
       *
INTO outputanomaly
FROM ArrayData
```

Or

```
with cte as
(
SELECT s.ArrayValue.* 
FROM inputopc
CROSS APPLY GetArrayElements(inputopc.[values]) s
)
select *
into outputanomaly from cte;
```

The above query pulls only one sensor and looks for anamoly and creates a record.

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/logicapp1.jpg "Architecture")

More to come...

Disclaimer: This is not official Microsoft approved industrial IoT Architecture.
