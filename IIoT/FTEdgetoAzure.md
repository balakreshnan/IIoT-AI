# Connect to Allen Bradly PLC using Azure IoT Edge OPC UA

## Author

- Balamurugan Balakreshnan
- Joseph Zaccaria
- Grant Richards
- Ragu Athinarayanan

## Industrial IoT

Industrial IoT is different from Regular IoT in terms of volume, velocity and veracity. In IoT scenario the amount of sensors to process is handful, whereas in industrial IoT it could be hundreds and thousands.

The frequency of data collected are some time it is 40milli second which could be 40 points a second in one sensor. Usually the 100 millisecond tags or sensors produce 10 points per second. There are also other sensor/tags that can be 1 second or 5 second and longer frequency.

<b>OPC UA </b> is the standard protocol which can used to connect to various others industrial PLC and control systems.

The main challenge is not collecting data from one PLC or Control system. But collecting from hundreds of PLC and Control System per line, and also multiple lines as well.

Given PLC's and control system are manufactured by so many different vendors with properiatry and common standards protocol to communicate are also other challenges.

To augument the above challenge if industry goes with OPC UA, then it makes it easier to integrate and connect and get insights.

Usually the PLC and Control Systems that connect mass producing lines are usually considered as OT - Operational Technology. They live in a separate network with line, in between lines with Factory or Plant or multiple of them. These networks has to be fast to able to communicate between PLC, Sensor and Control system - based on how fast decision are made in PLC, Sensor or Control Systems.
Control Systems are brain, that connects to multiple PLC's and Sensor's and not only collect data but also sends decision information to PLC, Sensor to take action on.

## Understand Industrial IoT

To understand industrial IoT and show value to business, we must understand how factories are built, what do they do?, How to we built a hybrid system that can store 6 to 1 years data in the plant to take immediate decision to run the plant. 

Now to get insights and help them improve, data should be sent to cloud for historical data for further processing. Current 6 months to 1 year data and also long term are stored in cloud for doing advanced analytics and better assist manufacturer to take data driven decision.

There is also another need to connect the Factory or Plant to business systems like ERP, CRM and others. This connection allows people centric manufacturing, which can be customized to 1 single unit instead of bulk production. If the Line can talk to IT systems in real time, then the order information and receipe for that order can be pulled and matched in line level.

There is still a gap in doing the above linkage. Business systems don't perform fast like the factory lines or plants equipment do. Bridging that gap is so very important. 

Our goal is to bridge the gap.

## Challenge of Insights

Other challenge is what do we do with the data. The way we can find a solution for that is to collect the data and place it into the hands of users who understand the data. Given them the tools that they can slice and dice the data and build value. Once the value is found, then we can built amazing insights for business users to know and understand and take data driven decision. This could be applied to plant floor users to corporate users as well.

## Curriculum

- Industrial Lines
- Industrial electronics like PLC, Sensor and Control Systems
- Industrial connectivity to Corporate and also Cloud
- Industrial Business systems like MES, MOM, OEE
- Industrial Receipe management
- Industrial Asset management
- Industrial Quality systems
- Industrial Reliability
- Industrial Material management and Inventory
- Lean and Six Sigma management
- Agile and Data Driven decision making
- Industrial integration - connecting various systems to gether - methodology
- Industrial Preventive maintenance
- industrial Predictive Mainteannce
- Industrial equipments management - like scales and inspections
- OSHA rules and ISO 86000 process for RCA
- Industrial Safety and health of workers
- Industrial Energy management
- Industrial Materials planning
- Industrial Shipping and logistics
- Intelligent Supply Chain
- Industrial Researcha and Development

## Architecture

- Using Control Logix to Azure Cloud. This option allows to control multiple PLC in Customer Site.

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/5370abcontrollogix1-1.jpg "Architecture")

## Steps to Connect

## Pre--requiste

- Azure account
- Micro 850 PLC
- Compact Logix Controller

## Configure Compact Logic

- Download Factory Talk Edge Gateway
- Need serial no
- Get compact logix controller

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/logixplc1.jpg "Architecture")

- Install Factory Talk Edge Gateway
- Configure Data source
- Configure Apps
- Load the tags
- Configure another source to Azure IoT
- Need device name
- Need device connection string
- Configure the tags to send
- Configure the frequency to send in this sample every 10 seconds
- Save and deploy the gateway to logix controller

## Azure Cloud Side

- Configure Stream analytics to read from IoT Hub and write to Blob for further process
- Write the query as

```
select 
gatewayData ,
PublisherId as deviceid,	
EventProcessedUtcTime as EventProcessedUtcTime,
EventEnqueuedUtcTime as EventEnqueuedUtcTime,
IoTHub.ConnectionDeviceId as ConnectionDeviceId,
i.IoTHub.EnqueuedTime as EnqueuedTime
into outputblob from input
```

- gatewayData is the property which is JSON has all the tags.

- Stream Analytics Input

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/rockfte1.jpg "Architecture")

- Stream Analytics output

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/rockfte2.jpg "Architecture")

- Here is the output from Strean analytics persisted into Azure ADLS gen2 storage

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/rockfte3.jpg "Architecture")

- More to come ...