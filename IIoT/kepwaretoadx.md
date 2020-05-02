# Industrial IOT - Kepware data collection to Azure Data Explorer

## Use Case

Provide a data store for industrial sensor, plc, control systems and other process based systems like MES and other plant floor systems. The system should also have the capability to query the Time Series data collected from various sensors from plant floor. The system can be data source for their dasboard, OEE and Root cause analysis and other quality based use cases.

## Challenge

Provide a single store where we can converge all the large volume manufacturing operations both process and industrial sensor data (time series) as well. It is easy to get the data and store in a cloud based storage but that doesn't allow us to use the query the data for realtime and batch use.

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/IndustrialIoT-Kepware1.jpg "Architecture")

## How to Solve the above challenge

Components Used

- Robots
- Line - MFG line that is manufacturing a product
- KepWare - IoT Gateway 
- Azure IoT Hub - Providing secure data communication from kepware to Cloud
- Azure Data Explorer - Cloud Historian Store
- Dashboard - Visual Tools

Now We can collect data from data collectors or gateway (industry terminology). The gateway i am choosing is PTC Kepware. There are also other means like OPC UA based tools to collect data.

Tools like Kepware has the capability collect data from older systems that are running in plant floor so much needed for retrofit use cases.

In a new plant OPC UA can be used as indsutrial standard for Industry 4.0 to collect data.

## Steps

## Install and Configure KepWare

First lets get Kepware installed and configure kepware.

Also configure which equipment to connect and subscribe for the tags what you need to send to cloud.

Here is a below sample on how Kepware sends data to IoT Hub.

```
[
    {
      "timestamp": 1586780606000,
      "values": [
        {
          "id": "Channel1.Device1.Tag1",
          "v": 250,
          "q": true,
          "t": 1586780606000
        },
        {
          "id": "Channel1.Device1.Tag2",
          "v": 220,
          "q": true,
          "t": 1586780606001
        },
        {
          "id": "Channel1.Device1.Tag3",
          "v": 150,
          "q": true,
          "t": 1586780606002
        }
      ]
    },
    {
      "timestamp": 1586780606030,
      "values": [
        {
          "id": "Channel1.Device1.Tag1",
          "v": 255,
          "q": true,
          "t": 1586780606031
        },
        {
          "id": "Channel1.Device1.Tag2",
          "v": 223,
          "q": true,
          "t": 1586780606032
        },
        {
          "id": "Channel1.Device1.Tag3",
          "v": 156,
          "q": true,
          "t": 1586780606033
        }
      ]
    },
    {
      "timestamp": 1586780606040,
      "values": [
        {
          "id": "Channel1.Device1.Tag1",
          "v": 251,
          "q": true,
          "t": 1586780606041
        },
        {
          "id": "Channel1.Device1.Tag2",
          "v": 229,
          "q": true,
          "t": 1586780606041
        },
        {
          "id": "Channel1.Device1.Tag3",
          "v": 153,
          "q": true,
          "t": 1586780606042
        }
      ]
    },
    {
      "timestamp": 1586780606060,
      "values": [
        {
          "id": "Channel1.Device1.Tag1",
          "v": 252,
          "q": true,
          "t": 1586780606061
        },
        {
          "id": "Channel1.Device1.Tag2",
          "v": 224,
          "q": true,
          "t": 1586780606062
        },
        {
          "id": "Channel1.Device1.Tag3",
          "v": 158,
          "q": true,
          "t": 1586780606063
        }
      ]
    }
  ]
```

Now to get the flowing through iot hub and then create a consumer group to fork for Azure Data Explorer.

Once in Data explorer it is time to create a data model to push the data in.

## Azure Data Explorer output:

Azure Data explorer allows us to do charting and trending by writing Kusto queries. Doesn't have the click and go environment. 

But Azure data explorer allow us to bring data from other business systems and then combine that seamlessly with time series data for queiring capability. So we can bring order data, Asset management data from Asset management system or ERP's and join them and build queiries.

Azure data explorer also allows us to expand and create our own dashboards using third party tools or custom web sites.

Azure Data explorer also has lot of time series functions already in built to Time series analysis like forecasting, Anomaly detection and even machine learning like auto clustering and basket analysis.

To get Azure data explorer start first create the schema to store the data

```
// Create table command
////////////////////////////////////////////////////////////
.create table ['kepwaresample_stage']  (['values']:dynamic, ['timestamp']:datetime)

// Set 0d retention on stage table so that the data is deleted after its transformed
.alter-merge table kepwaresample_stage policy retention softdelete = 0d

// Create mapping command
////////////////////////////////////////////////////////////
.create-or-alter table ['kepwaresample_stage'] ingestion json mapping 'kepwaresample_stage_mapping' '[{"column":"values","path":"$.values","datatype":"dynamic"},{"column":"timestamp","path":"$.timestamp","transform":"DateTimeFromUnixMilliseconds"}]'

//create function to extract the data from JSON 
.create-or-alter function TransformKepWareLogs()
{ 
kepwaresample_stage
| mv-expand values
| project 
msg_timestamp=timestamp,
metric_timestamp=unixtime_milliseconds_todatetime(tolong(values.t)),
metric_id=tostring(values.id), 
metric_value=tostring(values.v),
metric_quality=tobool(values.q)
}

//create the final table that will hold the extracted data
.create table kepwaresample (msg_timestamp: datetime, metric_timestamp: datetime, metric_id: string, metric_value: string, metric_quality: bool) 

//Create update policy to bind the stabing table, function, and the destination table 
.alter table kepwaresample policy update
@'[{"IsEnabled": true, "Source": "kepwaresample_stage", "Query": "TransformKepWareLogs()", "IsTransactional": true, "PropagateIngestionProperties": true}]'


// Ingest data into table command
///////////////////////////////////////////////////////////
.ingest async into table ['kepwaresample_stage'] ('https://kkgkstrldkustodemo00.blob.core.windows.net/pbp-20200413-temp-e5c334ee145d4b43a3a2d3a96fbac1df/1586805347662_kepwaresample.json?sv=2018-03-28&sr=c&sig=uvob%2BuNmKN1FeDFo983Ldft0Z%2BNputQhYQYad9nZWbE%3D&st=2020-04-13T18%3A15%3A47Z&se=2020-04-17T19%3A15%3A47Z&sp=rwdl') with (format='multijson',ingestionMappingReference='kepwaresample_stage_mapping',ingestionMappingType='Json',tags="['229fee5c-508d-4f26-99ae-3f2d007c813f']")

//from the above run get the id and substitute below here
.show operations 2d8b2cbc-2bf1-496b-99f0-75ed6fb1ee8f

kepwaresample
| limit 100
```

Now that the data is ready to be used for real time and other batch processing use cases.

Wait for few mins and see if the data is flowing. If data is flowing then we are all.

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
.create external table opcExternalLongTerm (msg_timestamp: datetime, metric_timestamp: datetime, metric_id: string, metric_value: string, metric_quality: bool) 
kind=blob
partition by 
   "metric_id="metric_id,
   bin(TimeStamp, 1d)
dataformat=csv
( 
   h@'https://xxxxxx.blob.core.windows.net/opcoutput;xxxxxxxxxxxxxxxxxxxxxxxxx'
)
```

Now configure the continous export data in Azure Data Explorer

```
.create-or-alter continuous-export opccontinousexport
over (kepwaresample)
to table opcExternalLongTerm
with
(intervalBetweenRuns=1h, 
 forcedLatency=10m, 
 sizeLimit=104857600)
<| kepwaresample
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