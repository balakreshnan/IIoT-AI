# MX Chips - Azure IoT Central Export data to Azure Data Explorer

## Using mxchips to push data out to Azure IoT Central to Azure Data Explorer

## Use Case

Ability to store Time Series historical data into Azure Data Explorer for time series processing. Also ablitiy to store 
ADLS gen 2 for long term storage and also for Machine learning or deep learning anlaytics.

I have been running this from:

03/18/2020 to 07/31/2020 and collected data points for the below tutorial.

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/mxchipIIoT.jpg "Architecture")

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
.create table ['iotcentraltelemetry']  (['humidity']:real, ['temperature']:real, ['pressure']:real, ['magnetometer_x']:int, ['magnetometer_y']:int, ['magnetometer_z']:int, ['gyroscope_x']:int, ['gyroscope_y']:int, ['gyroscope_z']:int, ['accelerometer_x']:int, ['accelerometer_y']:int, ['accelerometer_z']:int, ['EventProcessedUtcTime']:datetime, ['PartitionId']:int, ['EventEnqueuedUtcTime']:datetime,['SerialNo']:string)

// Create mapping command
////////////////////////////////////////////////////////////
.create table ['iotcentraltelemetry'] ingestion json mapping 'iotcentraltelemetry_mapping' '[{"column":"humidity","path":"$.humidity","datatype":"real"},{"column":"temperature","path":"$.temperature","datatype":"real"},{"column":"pressure","path":"$.pressure","datatype":"real"},{"column":"magnetometer_x","path":"$.magnetometer.x","datatype":"int"},{"column":"magnetometer_y","path":"$.magnetometer.y","datatype":"int"},{"column":"magnetometer_z","path":"$.magnetometer.z","datatype":"int"},{"column":"gyroscope_x","path":"$.gyroscope.x","datatype":"int"},{"column":"gyroscope_y","path":"$.gyroscope.y","datatype":"int"},{"column":"gyroscope_z","path":"$.gyroscope.z","datatype":"int"},{"column":"accelerometer_x","path":"$.accelerometer.x","datatype":"int"},{"column":"accelerometer_y","path":"$.accelerometer.y","datatype":"int"},{"column":"accelerometer_z","path":"$.accelerometer.z","datatype":"int"},{"column":"EventProcessedUtcTime","path":"$.EventProcessedUtcTime","datatype":"datetime"},{"column":"PartitionId","path":"$.PartitionId","datatype":"int"},{"column":"EventEnqueuedUtcTime","path":"$.EventEnqueuedUtcTime","datatype":"datetime"},{"column":"SerialNo","path":"$.SerialNo","datatype":"string"}]'
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

## Azure Data Explorer Cont

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

Lets do some charting

```
iotcentraltelemetry
| extend ingesttime = ingestion_time()
| project ingesttime,humidity,temperature,pressure, accelerometer_x,accelerometer_y,accelerometer_z
| summarize avgHumidity=avg(humidity) ,avgPressure=avg(pressure), avgTemperature=avg(temperature)
by bin(ingesttime, 1m)
| render timechart 
```

Now lets chart only 2 sensor points

```
iotcentraltelemetry
| extend ingesttime = ingestion_time()
| project ingesttime,humidity,temperature,pressure, accelerometer_x,accelerometer_y,accelerometer_z
| summarize avgHumidity=avg(humidity) ,avgTemperature=avg(temperature)
by bin(ingesttime, 1m)
| render timechart 
```

the above query aggregates every 1 minutes

Now lets aggregate data every 15 minute

```
iotcentraltelemetry
| extend ingesttime = ingestion_time()
| project ingesttime,humidity,temperature,pressure, accelerometer_x,accelerometer_y,accelerometer_z
| summarize avgHumidity=avg(humidity) ,avgTemperature=avg(temperature)
by bin(ingesttime, 15m)
| render timechart 
```

These queries are sample to do ETL on read rather than aggregating and storing it

Hourly Aggregate

```
iotcentraltelemetry
| extend ingesttime = ingestion_time()
| project ingesttime,humidity,temperature,pressure, accelerometer_x,accelerometer_y,accelerometer_z
| summarize avgHumidity=avg(humidity) ,avgTemperature=avg(temperature)
by bin(ingesttime, 1h)
| render timechart 
```

Daily averages

```
iotcentraltelemetry
| extend ingesttime = ingestion_time()
| project ingesttime,humidity,temperature,pressure, accelerometer_x,accelerometer_y,accelerometer_z
| summarize avgHumidity=avg(humidity) ,avgTemperature=avg(temperature)
by bin(ingesttime, 1d)
| render timechart 
```

Anamoly detection

```
let min_t = datetime(2020-03-18);
let max_t = datetime(2020-03-19 22:00);
let dt = 1h;
iotcentraltelemetry
| extend ingesttime = ingestion_time() 
| make-series temperature=avg(temperature) on ingesttime from min_t to max_t step dt
| extend (anomalies, score, baseline) = series_decompose_anomalies(temperature, 1.5, -1, 'linefit')
| render anomalychart with(anomalycolumns=anomalies, title='Temp, anomalies') 
```

Time series forecasting

```
let min_t = datetime(2020-03-18);
let max_t = datetime(2020-03-19 22:00);
let dt = 1h;
let horizon=7d;
iotcentraltelemetry
| extend ingesttime = ingestion_time() 
| make-series temperature=avg(temperature) on ingesttime from min_t to max_t step dt
| extend forecast = series_decompose_forecast(temperature, toint(horizon/dt))
| render timechart with(title='Temp, forecasting the next week by Time Series Decomposition')
```

Machine learning

Clustering sample

```
let min_t = toscalar(iotcentraltelemetry | extend ingesttime = ingestion_time()  | summarize min(ingesttime));  
let max_t = toscalar(iotcentraltelemetry  | extend ingesttime = ingestion_time() | summarize max(ingesttime));  
iotcentraltelemetry
| extend ingesttime = ingestion_time() 
| make-series num=count() on ingesttime from min_t to max_t step 10m
| render timechart with(title="Temperature over a week, 10 minutes resolution")
```

```
let min_t=datetime(2020-03-18);
iotcentraltelemetry
| extend ingesttime = ingestion_time() 
| make-series num=count() on ingesttime from min_t to min_t+24h step 1m
| render timechart with(title="Zoom on the 2nd spike, 1 minute resolution")
```

AutoCluster

```
let min_peak_t=datetime(2020-03-18);
let max_peak_t=datetime(2020-03-19 22:00);
iotcentraltelemetry
| extend ingesttime = ingestion_time() 
| where ingesttime between(min_peak_t..max_peak_t)
| evaluate autocluster()
```

```
let min_peak_t=datetime(2020-03-18);
let max_peak_t=datetime(2020-03-19 22:00);
iotcentraltelemetry
| extend ingesttime = ingestion_time() 
| where ingesttime between(min_peak_t..max_peak_t)
| evaluate basket()
```

End of machine learning queries

## Forecasting

Run Forecasting on various sensors like temperature, humidity and pressure

```
iotcentraltelemetry
| extend ingestionTime = ingestion_time()
| where ingestionTime > ago(5h)
| make-series AvgTempWithDefault=avg(temperature) default=real(null) on ingestionTime from ago(5h) to now() step 1m
| extend NoGapsTemp=series_fill_linear(AvgTempWithDefault)
| project ingestionTime, NoGapsTemp
| render timechart

// What will be the temperature for next 15 minutes?
iotcentraltelemetry
| extend ingestionTime = ingestion_time()
| where ingestionTime > ago(5h)
| make-series AvgTemp=avg(temperature) default=real(null) on ingestionTime from ago(5h) to now()+120m step 1m
| extend NoGapsTemp=series_fill_linear(AvgTemp)
| project ingestionTime, NoGapsTemp
| extend forecast = series_decompose_forecast(NoGapsTemp, 15)
| render timechart with(title='Forecasting for next 15 min by Time Series Decomposition')

// What will be the temperature for next 15 minutes?
iotcentraltelemetry
| extend ingestionTime = ingestion_time()
| where ingestionTime > ago(5h)
| make-series AvgTemp=avg(humidity) default=real(null) on ingestionTime from ago(5h) to now()+15m step 1m
| extend NoGapsTemp=series_fill_linear(AvgTemp)
| project ingestionTime, NoGapsTemp
| extend forecast = series_decompose_forecast(NoGapsTemp, 15)
| render timechart with(title='Forecasting for next 15 min by Time Series Decomposition')

// What will be the temperature for next 15 minutes?
iotcentraltelemetry
| extend ingestionTime = ingestion_time()
| where ingestionTime > ago(5h)
| make-series AvgTemp=avg(pressure) default=real(null) on ingestionTime from ago(5h) to now()+120m step 1m
| extend NoGapsTemp=series_fill_linear(AvgTemp)
| project ingestionTime, NoGapsTemp
| extend forecast = series_decompose_forecast(NoGapsTemp, 15)
| render timechart with(title='Forecasting for next 15 min by Time Series Decomposition')
```

Here is the output for all data collected

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/4monthshistory2020.JPG "History")

Good Luck and Have fun experimenting mxchips and other IoT Devices