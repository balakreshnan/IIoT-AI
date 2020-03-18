# Azure IoT Central Export data to Azure Data Explorer and ADLS gen2 for long term storage.

## Using mxchips to push data out to Azure IoT Central to Azure Data Explorer.

## Use Case

Ability to store Time Series historical data into Azure Data Explorer for time series processing. Also ablitiy to store 
ADLS gen 2 for long term storage and also for Machine learning or deep learning anlaytics.

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/Iotcentraltoadx.jpg "Architecture")

First configure the mxchips and connect to Azure Iot Central using this link: 
https://docs.microsoft.com/en-us/samples/azure-samples/mxchip-iot-devkit-pnp/sample/

Github Repo:
https://github.com/Azure-Samples/mxchip-iot-devkit-pnp

Once you have the data streaming into Azure IoT Central and validated using the dashboard.

Now time to Setup

- Azure Storage Account
- Azure Event Hub
- Azure Data Explorer

## Azure Storage Account

Create a blob storage account and create a container called iotcentralTelemetry. 
From Azure IoT Central options to export Telemetry, devices and Device template are available at the time writing this blob.

When Exported there should be 3 folders 

iotcentraltelemetry\GUID\telemetry
iotcentraltelemetry\GUID\devices
iotcentraltelemetry\GUID\deviceTemplates

## Azure Event Hub

Create a Event hub name space.

Now create 3 Event hub one for Telemetry, one for Devices, one for Device Telemetry.

I named it as

- iotcentraltelemetry
- iotcentraldevices
- iotcentraldevicetemplates

Leave the default 2 parition and select all defaults for the testing.

## Azure Data Explorer

Now create a New database if needed or use existing data base. Our goal is to only push telemetry into ADX.

Now it is time to create table, table mapping

```
// Create table command
////////////////////////////////////////////////////////////
.create table ['iotcentraltelemetry']  (['humidity']:real, ['temperature']:real, ['pressure']:real, ['magnetometer_x']:int, ['magnetometer_y']:int, ['magnetometer_z']:int, ['gyroscope_x']:int, ['gyroscope_y']:int, ['gyroscope_z']:int, ['accelerometer_x']:int, ['accelerometer_y']:int, ['accelerometer_z']:int, ['EventProcessedUtcTime']:datetime, ['PartitionId']:int, ['EventEnqueuedUtcTime']:datetime)

// Create mapping command
////////////////////////////////////////////////////////////
.create table ['iotcentraltelemetry'] ingestion json mapping 'iotcentraltelemetry_mapping' '[{"column":"humidity","path":"$.humidity","datatype":"real"},{"column":"temperature","path":"$.temperature","datatype":"real"},{"column":"pressure","path":"$.pressure","datatype":"real"},{"column":"magnetometer_x","path":"$.magnetometer.x","datatype":"int"},{"column":"magnetometer_y","path":"$.magnetometer.y","datatype":"int"},{"column":"magnetometer_z","path":"$.magnetometer.z","datatype":"int"},{"column":"gyroscope_x","path":"$.gyroscope.x","datatype":"int"},{"column":"gyroscope_y","path":"$.gyroscope.y","datatype":"int"},{"column":"gyroscope_z","path":"$.gyroscope.z","datatype":"int"},{"column":"accelerometer_x","path":"$.accelerometer.x","datatype":"int"},{"column":"accelerometer_y","path":"$.accelerometer.y","datatype":"int"},{"column":"accelerometer_z","path":"$.accelerometer.z","datatype":"int"},{"column":"EventProcessedUtcTime","path":"$.EventProcessedUtcTime","datatype":"datetime"},{"column":"PartitionId","path":"$.PartitionId","datatype":"int"},{"column":"EventEnqueuedUtcTime","path":"$.EventEnqueuedUtcTime","datatype":"datetime"}]'
```

Table name : iotcentraltelemetry

Table Mapping name:  iotcentraltelemetry_mapping

Mapping type JSON

## Azure IoT Central

Log into IoT central dashboard

Go to Export and then configure 2 options.

- Export to Blob Storage
- Export to Event Hub

For Blob storage select the above iotcentraltelemetry container as output. You can select all 3 telemetry, devices and deviceTemplates to output or choice is yours. 

Data is stored every minute and stored as JSON format file.

For the Event hub Select only telemetry and select the iotcentraltelemetry as event hub name. Event hub name space are shown in drop down box.

Click Create and wait until it is running.

## Azure Data Explorer

Now go into Azure data explorer.

Select the database we are going to use. On the left menu we have option for data ingestion.

Select data ingestion and create a new ingestion connection and select the Event hub as iotcentraltelemetry.

For ADX table name use: iotcentraltelemetry

For mapping name: iotcentraltelemetry_mapping

For data Type use: JSON

Validate and create the connection and wait for data to load

now you can run query to validate and see if the data is loaded

```
iotcentraltelemetry
| limit 200
```