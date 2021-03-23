# Connect to Allen Bradly PLC using Azure IoT Edge MQTT

## Author

- Balamurugan Balakreshnan
- Joseph Zaccaria
- Grant Richards
- Ragu Athinarayanan
- Huachao Mao
- Gaurav Nanda


## Industrial IoT

Industrial IoT is different from Regular IoT in terms of volume, velocity and veracity. In IoT scenario the amount of sensors to process is handful, whereas in industrial IoT it could be hundreds and thousands.

The frequency of data collected are some time it is 40milli second which could be 40 points a second in one sensor. Usually the 100 millisecond tags or sensors produce 10 points per second. There are also other sensor/tags that can be 1 second or 5 second and longer frequency.

The main challenge is not collecting data from one PLC or Control system. But collecting from hundreds of PLC and Control System per line, and also multiple lines as well.

Given PLC's and control system are manufactured by so many different vendors with properiatry and common standards protocol to communicate are also other challenges.

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

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/IIoT-Arch1.jpg "Architecture")

## Steps to Connect

## Pre--requiste

- Azure account
- Micro 850 PLC
- Compact Logix Controller

## Configure Compact Logic 5480

- Download Factory Talk Edge Gateway
- Need serial no
- Get compact logix controller 5480
- Compute module
- Gateway installs in compute module
- Install Factory Talk Edge Gateway
- Configure Data source
- Configure Apps
- Load the tags
- Configure data time to match current data time
- Configure another source to Azure IoT
- Need device name
- Need device connection string
- Data pushed using mqtt
- Configure the tags to send
- Configure the frequency to send in this sample every 10 seconds
- Save and deploy the gateway to logix controller
- Logix is installed in compact logix.
- Windows IOT 10
- Open Firewall ports

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/FTEEDge.jpg "Architecture")

## Steps to program Compact Logix 5480

- Create CIP driver, should be the local compute module's IP address
- To get the ip log using monitor and type ipconfig
- create data source
- One for the PLC in compact logix 5480 
- IP usually displayed in the LED display.
- Create a Model
- Model can group the tags and apply to different outputs
- Create a Application
- Here we are creating IOT Hub
- Specify the name
- Specify the device name
- Use connection string (device)
- Select configure and select the tags and specify frequency to send
- Deploy the code
- Click Enable data flow
- Save settings
- Wait for the system should show all green
- Select Store and Forward - to keep the data if it goes down

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/purduekit.jpg "Architecture")

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/fte0.jpg "Architecture")

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/fte1.jpg "Architecture")

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/fte2.jpg "Architecture")

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

- Sample Data set with simulated values

- From Compact Logix 5480 with Factory Talk Edge Gateway Installed in compute module - Industrialized

```
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2463,"q":192,"t":"2020-11-23T21:49:38.121Z"},{"v":2467,"q":192,"t":"2020-11-23T21:49:40.121Z"},{"v":2471,"q":192,"t":"2020-11-23T21:49:42.137Z"},{"v":2475,"q":192,"t":"2020-11-23T21:49:44.151Z"},{"v":2479,"q":192,"t":"2020-11-23T21:49:46.151Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":115,"q":192,"t":"2020-11-23T21:49:42.137Z"},{"v":129,"q":192,"t":"2020-11-23T21:49:44.151Z"},{"v":130,"q":192,"t":"2020-11-23T21:49:46.151Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:49:46.1833795Z","EventEnqueuedUtcTime":"2020-11-23T21:49:46.1080000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2483,"q":192,"t":"2020-11-23T21:49:48.151Z"},{"v":2487,"q":192,"t":"2020-11-23T21:49:50.151Z"},{"v":2491,"q":192,"t":"2020-11-23T21:49:52.150Z"},{"v":2495,"q":192,"t":"2020-11-23T21:49:54.150Z"},{"v":2499,"q":192,"t":"2020-11-23T21:49:56.150Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":129,"q":192,"t":"2020-11-23T21:49:48.151Z"},{"v":128,"q":192,"t":"2020-11-23T21:49:52.150Z"},{"v":129,"q":192,"t":"2020-11-23T21:49:54.150Z"},{"v":128,"q":192,"t":"2020-11-23T21:49:56.150Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:49:56.1361665Z","EventEnqueuedUtcTime":"2020-11-23T21:49:56.1110000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2503,"q":192,"t":"2020-11-23T21:49:58.150Z"},{"v":2507,"q":192,"t":"2020-11-23T21:50:00.150Z"},{"v":2511,"q":192,"t":"2020-11-23T21:50:02.150Z"},{"v":2515,"q":192,"t":"2020-11-23T21:50:04.149Z"},{"v":2519,"q":192,"t":"2020-11-23T21:50:06.149Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":127,"q":192,"t":"2020-11-23T21:50:04.149Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:50:06.4365240Z","EventEnqueuedUtcTime":"2020-11-23T21:50:06.2220000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2523,"q":192,"t":"2020-11-23T21:50:08.149Z"},{"v":2527,"q":192,"t":"2020-11-23T21:50:10.148Z"},{"v":2531,"q":192,"t":"2020-11-23T21:50:12.148Z"},{"v":2535,"q":192,"t":"2020-11-23T21:50:14.148Z"},{"v":2539,"q":192,"t":"2020-11-23T21:50:16.148Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":126,"q":192,"t":"2020-11-23T21:50:12.148Z"},{"v":127,"q":192,"t":"2020-11-23T21:50:14.148Z"},{"v":126,"q":192,"t":"2020-11-23T21:50:16.148Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:50:16.4118737Z","EventEnqueuedUtcTime":"2020-11-23T21:50:16.3810000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2543,"q":192,"t":"2020-11-23T21:50:18.148Z"},{"v":2547,"q":192,"t":"2020-11-23T21:50:20.148Z"},{"v":2551,"q":192,"t":"2020-11-23T21:50:22.147Z"},{"v":2555,"q":192,"t":"2020-11-23T21:50:24.147Z"},{"v":2559,"q":192,"t":"2020-11-23T21:50:26.146Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":125,"q":192,"t":"2020-11-23T21:50:24.147Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:50:26.4979927Z","EventEnqueuedUtcTime":"2020-11-23T21:50:26.4290000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2563,"q":192,"t":"2020-11-23T21:50:28.146Z"},{"v":2567,"q":192,"t":"2020-11-23T21:50:30.146Z"},{"v":2571,"q":192,"t":"2020-11-23T21:50:32.146Z"},{"v":2575,"q":192,"t":"2020-11-23T21:50:34.146Z"},{"v":2579,"q":192,"t":"2020-11-23T21:50:36.145Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":124,"q":192,"t":"2020-11-23T21:50:30.146Z"},{"v":125,"q":192,"t":"2020-11-23T21:50:32.146Z"},{"v":124,"q":192,"t":"2020-11-23T21:50:36.145Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:50:36.4770011Z","EventEnqueuedUtcTime":"2020-11-23T21:50:36.2590000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2583,"q":192,"t":"2020-11-23T21:50:38.145Z"},{"v":2587,"q":192,"t":"2020-11-23T21:50:40.145Z"},{"v":2591,"q":192,"t":"2020-11-23T21:50:42.145Z"},{"v":2595,"q":192,"t":"2020-11-23T21:50:44.144Z"},{"v":2599,"q":192,"t":"2020-11-23T21:50:46.144Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":123,"q":192,"t":"2020-11-23T21:50:38.145Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:50:46.4481406Z","EventEnqueuedUtcTime":"2020-11-23T21:50:46.3090000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2603,"q":192,"t":"2020-11-23T21:50:48.144Z"},{"v":2607,"q":192,"t":"2020-11-23T21:50:50.144Z"},{"v":2611,"q":192,"t":"2020-11-23T21:50:52.143Z"},{"v":2615,"q":192,"t":"2020-11-23T21:50:54.143Z"},{"v":2619,"q":192,"t":"2020-11-23T21:50:56.143Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":122,"q":192,"t":"2020-11-23T21:50:48.144Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:50:56.7351501Z","EventEnqueuedUtcTime":"2020-11-23T21:50:56.5180000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2623,"q":192,"t":"2020-11-23T21:50:58.143Z"},{"v":2627,"q":192,"t":"2020-11-23T21:51:00.143Z"},{"v":2631,"q":192,"t":"2020-11-23T21:51:02.142Z"},{"v":2635,"q":192,"t":"2020-11-23T21:51:04.142Z"},{"v":2639,"q":192,"t":"2020-11-23T21:51:06.142Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":121,"q":192,"t":"2020-11-23T21:50:58.143Z"},{"v":120,"q":192,"t":"2020-11-23T21:51:06.142Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:51:06.7012628Z","EventEnqueuedUtcTime":"2020-11-23T21:51:06.5690000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2643,"q":192,"t":"2020-11-23T21:51:08.142Z"},{"v":2647,"q":192,"t":"2020-11-23T21:51:10.141Z"},{"v":2651,"q":192,"t":"2020-11-23T21:51:12.141Z"},{"v":2655,"q":192,"t":"2020-11-23T21:51:14.141Z"},{"v":2659,"q":192,"t":"2020-11-23T21:51:16.141Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":119,"q":192,"t":"2020-11-23T21:51:16.141Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:51:16.7784433Z","EventEnqueuedUtcTime":"2020-11-23T21:51:16.5690000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2663,"q":192,"t":"2020-11-23T21:51:18.141Z"},{"v":2667,"q":192,"t":"2020-11-23T21:51:20.140Z"},{"v":2671,"q":192,"t":"2020-11-23T21:51:22.140Z"},{"v":2675,"q":192,"t":"2020-11-23T21:51:24.140Z"},{"v":2679,"q":192,"t":"2020-11-23T21:51:26.140Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":118,"q":192,"t":"2020-11-23T21:51:26.140Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:51:26.7485301Z","EventEnqueuedUtcTime":"2020-11-23T21:51:26.5890000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2683,"q":192,"t":"2020-11-23T21:51:28.139Z"},{"v":2687,"q":192,"t":"2020-11-23T21:51:30.139Z"},{"v":2691,"q":192,"t":"2020-11-23T21:51:32.139Z"},{"v":2695,"q":192,"t":"2020-11-23T21:51:34.139Z"},{"v":2699,"q":192,"t":"2020-11-23T21:51:36.139Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":117,"q":192,"t":"2020-11-23T21:51:36.139Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:51:36.8740273Z","EventEnqueuedUtcTime":"2020-11-23T21:51:36.8110000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2703,"q":192,"t":"2020-11-23T21:51:38.138Z"},{"v":2707,"q":192,"t":"2020-11-23T21:51:40.138Z"},{"v":2711,"q":192,"t":"2020-11-23T21:51:42.138Z"},{"v":2715,"q":192,"t":"2020-11-23T21:51:44.137Z"},{"v":2719,"q":192,"t":"2020-11-23T21:51:46.137Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":116,"q":192,"t":"2020-11-23T21:51:44.137Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:51:47.0787874Z","EventEnqueuedUtcTime":"2020-11-23T21:51:46.8590000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2723,"q":192,"t":"2020-11-23T21:51:48.137Z"},{"v":2727,"q":192,"t":"2020-11-23T21:51:50.136Z"},{"v":2731,"q":192,"t":"2020-11-23T21:51:52.136Z"},{"v":2735,"q":192,"t":"2020-11-23T21:51:54.135Z"},{"v":2739,"q":192,"t":"2020-11-23T21:51:56.135Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":115,"q":192,"t":"2020-11-23T21:51:54.135Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:51:56.9533568Z","EventEnqueuedUtcTime":"2020-11-23T21:51:56.8790000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2743,"q":192,"t":"2020-11-23T21:51:58.135Z"},{"v":2747,"q":192,"t":"2020-11-23T21:52:00.134Z"},{"v":2751,"q":192,"t":"2020-11-23T21:52:02.134Z"},{"v":2755,"q":192,"t":"2020-11-23T21:52:04.134Z"},{"v":2759,"q":192,"t":"2020-11-23T21:52:06.133Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":114,"q":192,"t":"2020-11-23T21:52:02.134Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:52:07.1211850Z","EventEnqueuedUtcTime":"2020-11-23T21:52:06.9110000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2763,"q":192,"t":"2020-11-23T21:52:08.133Z"},{"v":2767,"q":192,"t":"2020-11-23T21:52:10.132Z"},{"v":2771,"q":192,"t":"2020-11-23T21:52:12.132Z"},{"v":2775,"q":192,"t":"2020-11-23T21:52:14.132Z"},{"v":2779,"q":192,"t":"2020-11-23T21:52:16.131Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":113,"q":192,"t":"2020-11-23T21:52:12.132Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:52:16.9734414Z","EventEnqueuedUtcTime":"2020-11-23T21:52:16.7610000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2783,"q":192,"t":"2020-11-23T21:52:18.131Z"},{"v":2787,"q":192,"t":"2020-11-23T21:52:20.131Z"},{"v":2791,"q":192,"t":"2020-11-23T21:52:22.130Z"},{"v":2795,"q":192,"t":"2020-11-23T21:52:24.130Z"},{"v":2799,"q":192,"t":"2020-11-23T21:52:26.129Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":112,"q":192,"t":"2020-11-23T21:52:22.130Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:52:27.1614027Z","EventEnqueuedUtcTime":"2020-11-23T21:52:26.9970000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2803,"q":192,"t":"2020-11-23T21:52:28.129Z"},{"v":2807,"q":192,"t":"2020-11-23T21:52:30.129Z"},{"v":2811,"q":192,"t":"2020-11-23T21:52:32.128Z"},{"v":2815,"q":192,"t":"2020-11-23T21:52:34.128Z"},{"v":2819,"q":192,"t":"2020-11-23T21:52:36.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":111,"q":192,"t":"2020-11-23T21:52:32.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:52:37.0639742Z","EventEnqueuedUtcTime":"2020-11-23T21:52:36.8750000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2823,"q":192,"t":"2020-11-23T21:52:38.127Z"},{"v":2827,"q":192,"t":"2020-11-23T21:52:40.127Z"},{"v":2831,"q":192,"t":"2020-11-23T21:52:42.126Z"},{"v":2835,"q":192,"t":"2020-11-23T21:52:44.126Z"},{"v":2839,"q":192,"t":"2020-11-23T21:52:46.126Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":110,"q":192,"t":"2020-11-23T21:52:42.126Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:52:47.1371055Z","EventEnqueuedUtcTime":"2020-11-23T21:52:47.0640000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2843,"q":192,"t":"2020-11-23T21:52:48.125Z"},{"v":2847,"q":192,"t":"2020-11-23T21:52:50.125Z"},{"v":2851,"q":192,"t":"2020-11-23T21:52:52.124Z"},{"v":2855,"q":192,"t":"2020-11-23T21:52:54.124Z"},{"v":2859,"q":192,"t":"2020-11-23T21:52:56.124Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":109,"q":192,"t":"2020-11-23T21:52:50.125Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:52:56.9702610Z","EventEnqueuedUtcTime":"2020-11-23T21:52:56.8970000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2863,"q":192,"t":"2020-11-23T21:52:58.124Z"},{"v":2867,"q":192,"t":"2020-11-23T21:53:00.123Z"},{"v":2871,"q":192,"t":"2020-11-23T21:53:02.123Z"},{"v":2875,"q":192,"t":"2020-11-23T21:53:04.122Z"},{"v":2879,"q":192,"t":"2020-11-23T21:53:06.122Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":108,"q":192,"t":"2020-11-23T21:53:00.123Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:53:06.9348465Z","EventEnqueuedUtcTime":"2020-11-23T21:53:06.9290000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2883,"q":192,"t":"2020-11-23T21:53:08.121Z"},{"v":2887,"q":192,"t":"2020-11-23T21:53:10.121Z"},{"v":2891,"q":192,"t":"2020-11-23T21:53:12.121Z"},{"v":2895,"q":192,"t":"2020-11-23T21:53:14.120Z"},{"v":2899,"q":192,"t":"2020-11-23T21:53:16.120Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":107,"q":192,"t":"2020-11-23T21:53:08.121Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:53:17.3531063Z","EventEnqueuedUtcTime":"2020-11-23T21:53:17.1500000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2903,"q":192,"t":"2020-11-23T21:53:18.120Z"},{"v":2907,"q":192,"t":"2020-11-23T21:53:20.119Z"},{"v":2911,"q":192,"t":"2020-11-23T21:53:22.119Z"},{"v":2915,"q":192,"t":"2020-11-23T21:53:24.118Z"},{"v":2919,"q":192,"t":"2020-11-23T21:53:26.118Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":106,"q":192,"t":"2020-11-23T21:53:18.120Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:53:27.2326452Z","EventEnqueuedUtcTime":"2020-11-23T21:53:27.1510000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2923,"q":192,"t":"2020-11-23T21:53:28.118Z"},{"v":2927,"q":192,"t":"2020-11-23T21:53:30.133Z"},{"v":2931,"q":192,"t":"2020-11-23T21:53:32.133Z"},{"v":2935,"q":192,"t":"2020-11-23T21:53:34.132Z"},{"v":2939,"q":192,"t":"2020-11-23T21:53:36.132Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":105,"q":192,"t":"2020-11-23T21:53:28.118Z"},{"v":121,"q":192,"t":"2020-11-23T21:53:30.133Z"},{"v":120,"q":192,"t":"2020-11-23T21:53:34.132Z"},{"v":121,"q":192,"t":"2020-11-23T21:53:36.132Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:53:37.3242021Z","EventEnqueuedUtcTime":"2020-11-23T21:53:37.1840000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2943,"q":192,"t":"2020-11-23T21:53:38.131Z"},{"v":2947,"q":192,"t":"2020-11-23T21:53:40.131Z"},{"v":2951,"q":192,"t":"2020-11-23T21:53:42.131Z"},{"v":2955,"q":192,"t":"2020-11-23T21:53:44.130Z"},{"v":2959,"q":192,"t":"2020-11-23T21:53:46.130Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":120,"q":192,"t":"2020-11-23T21:53:38.131Z"},{"v":119,"q":192,"t":"2020-11-23T21:53:44.130Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:53:47.2785681Z","EventEnqueuedUtcTime":"2020-11-23T21:53:47.2310000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":2963,"q":192,"t":"2020-11-23T21:53:48.130Z"},{"v":2967,"q":192,"t":"2020-11-23T21:53:50.129Z"},{"v":2971,"q":192,"t":"2020-11-23T21:53:52.129Z"},{"v":2975,"q":192,"t":"2020-11-23T21:53:54.128Z"},{"v":2979,"q":192,"t":"2020-11-23T21:53:56.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":118,"q":192,"t":"2020-11-23T21:53:54.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T21:53:57.4748053Z","EventEnqueuedUtcTime":"2020-11-23T21:53:57.2630000Z","ConnectionDeviceId":"joezlogix","EnqueuedTime":null}
```

- From VM - Factory Talk edge gateway in VM

```
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":30,"q":192,"t":"2020-11-23T22:11:02.129Z"},{"v":30,"q":192,"t":"2020-11-23T22:11:02.129Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":249,"q":192,"t":"2020-11-23T22:11:02.129Z"},{"v":249,"q":192,"t":"2020-11-23T22:11:02.129Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:02.6809431Z","EventEnqueuedUtcTime":"2020-11-23T22:11:02.6570000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":40,"q":192,"t":"2020-11-23T22:11:07.128Z"},{"v":40,"q":192,"t":"2020-11-23T22:11:07.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":248,"q":192,"t":"2020-11-23T22:11:07.128Z"},{"v":248,"q":192,"t":"2020-11-23T22:11:07.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:07.7218089Z","EventEnqueuedUtcTime":"2020-11-23T22:11:07.5790000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":50,"q":192,"t":"2020-11-23T22:11:12.128Z"},{"v":50,"q":192,"t":"2020-11-23T22:11:12.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":247,"q":192,"t":"2020-11-23T22:11:12.128Z"},{"v":247,"q":192,"t":"2020-11-23T22:11:12.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:12.6500721Z","EventEnqueuedUtcTime":"2020-11-23T22:11:12.4860000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":60,"q":192,"t":"2020-11-23T22:11:17.129Z"},{"v":60,"q":192,"t":"2020-11-23T22:11:17.129Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":248,"q":192,"t":"2020-11-23T22:11:17.129Z"},{"v":248,"q":192,"t":"2020-11-23T22:11:17.129Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:17.4702995Z","EventEnqueuedUtcTime":"2020-11-23T22:11:17.4400000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":70,"q":192,"t":"2020-11-23T22:11:22.128Z"},{"v":70,"q":192,"t":"2020-11-23T22:11:22.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":247,"q":192,"t":"2020-11-23T22:11:22.128Z"},{"v":247,"q":192,"t":"2020-11-23T22:11:22.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:22.6177120Z","EventEnqueuedUtcTime":"2020-11-23T22:11:22.5500000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":80,"q":192,"t":"2020-11-23T22:11:27.128Z"},{"v":80,"q":192,"t":"2020-11-23T22:11:27.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":246,"q":192,"t":"2020-11-23T22:11:27.128Z"},{"v":246,"q":192,"t":"2020-11-23T22:11:27.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:27.6589156Z","EventEnqueuedUtcTime":"2020-11-23T22:11:27.4880000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":90,"q":192,"t":"2020-11-23T22:11:32.127Z"},{"v":90,"q":192,"t":"2020-11-23T22:11:32.127Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":245,"q":192,"t":"2020-11-23T22:11:32.127Z"},{"v":245,"q":192,"t":"2020-11-23T22:11:32.127Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:32.6062141Z","EventEnqueuedUtcTime":"2020-11-23T22:11:32.4410000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":100,"q":192,"t":"2020-11-23T22:11:37.128Z"},{"v":100,"q":192,"t":"2020-11-23T22:11:37.128Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":244,"q":192,"t":"2020-11-23T22:11:37.128Z"},{"v":244,"q":192,"t":"2020-11-23T22:11:37.128Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:37.7596024Z","EventEnqueuedUtcTime":"2020-11-23T22:11:37.5680000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":110,"q":192,"t":"2020-11-23T22:11:42.126Z"},{"v":110,"q":192,"t":"2020-11-23T22:11:42.126Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":243,"q":192,"t":"2020-11-23T22:11:42.126Z"},{"v":243,"q":192,"t":"2020-11-23T22:11:42.126Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:42.6835172Z","EventEnqueuedUtcTime":"2020-11-23T22:11:42.5260000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":120,"q":192,"t":"2020-11-23T22:11:47.126Z"},{"v":120,"q":192,"t":"2020-11-23T22:11:47.126Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":243,"q":192,"t":"2020-11-23T22:11:47.126Z"},{"v":243,"q":192,"t":"2020-11-23T22:11:47.126Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:47.5969757Z","EventEnqueuedUtcTime":"2020-11-23T22:11:47.4800000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":130,"q":192,"t":"2020-11-23T22:11:52.126Z"},{"v":130,"q":192,"t":"2020-11-23T22:11:52.126Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":243,"q":192,"t":"2020-11-23T22:11:52.126Z"},{"v":243,"q":192,"t":"2020-11-23T22:11:52.126Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:52.5217810Z","EventEnqueuedUtcTime":"2020-11-23T22:11:52.4340000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":140,"q":192,"t":"2020-11-23T22:11:57.139Z"},{"v":140,"q":192,"t":"2020-11-23T22:11:57.139Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":254,"q":192,"t":"2020-11-23T22:11:57.139Z"},{"v":254,"q":192,"t":"2020-11-23T22:11:57.139Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:11:57.5539124Z","EventEnqueuedUtcTime":"2020-11-23T22:11:57.3720000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":150,"q":192,"t":"2020-11-23T22:12:02.126Z"},{"v":150,"q":192,"t":"2020-11-23T22:12:02.126Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":243,"q":192,"t":"2020-11-23T22:12:02.126Z"},{"v":243,"q":192,"t":"2020-11-23T22:12:02.126Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:02.5030862Z","EventEnqueuedUtcTime":"2020-11-23T22:12:02.4820000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":160,"q":192,"t":"2020-11-23T22:12:07.125Z"},{"v":160,"q":192,"t":"2020-11-23T22:12:07.125Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":240,"q":192,"t":"2020-11-23T22:12:07.125Z"},{"v":240,"q":192,"t":"2020-11-23T22:12:07.125Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:07.5359590Z","EventEnqueuedUtcTime":"2020-11-23T22:12:07.4350000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":170,"q":192,"t":"2020-11-23T22:12:12.124Z"},{"v":170,"q":192,"t":"2020-11-23T22:12:12.124Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":240,"q":192,"t":"2020-11-23T22:12:12.124Z"},{"v":240,"q":192,"t":"2020-11-23T22:12:12.124Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:12.4814710Z","EventEnqueuedUtcTime":"2020-11-23T22:12:12.3420000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":180,"q":192,"t":"2020-11-23T22:12:17.124Z"},{"v":180,"q":192,"t":"2020-11-23T22:12:17.124Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":239,"q":192,"t":"2020-11-23T22:12:17.124Z"},{"v":239,"q":192,"t":"2020-11-23T22:12:17.124Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:17.5365238Z","EventEnqueuedUtcTime":"2020-11-23T22:12:17.3110000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":190,"q":192,"t":"2020-11-23T22:12:22.125Z"},{"v":190,"q":192,"t":"2020-11-23T22:12:22.125Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":239,"q":192,"t":"2020-11-23T22:12:22.125Z"},{"v":239,"q":192,"t":"2020-11-23T22:12:22.125Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:22.5709008Z","EventEnqueuedUtcTime":"2020-11-23T22:12:22.4380000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":200,"q":192,"t":"2020-11-23T22:12:27.123Z"},{"v":200,"q":192,"t":"2020-11-23T22:12:27.123Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":238,"q":192,"t":"2020-11-23T22:12:27.123Z"},{"v":238,"q":192,"t":"2020-11-23T22:12:27.123Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:27.5146908Z","EventEnqueuedUtcTime":"2020-11-23T22:12:27.3290000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":210,"q":192,"t":"2020-11-23T22:12:32.123Z"},{"v":210,"q":192,"t":"2020-11-23T22:12:32.123Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":237,"q":192,"t":"2020-11-23T22:12:32.123Z"},{"v":237,"q":192,"t":"2020-11-23T22:12:32.123Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:32.5505367Z","EventEnqueuedUtcTime":"2020-11-23T22:12:32.4550000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":220,"q":192,"t":"2020-11-23T22:12:37.123Z"},{"v":220,"q":192,"t":"2020-11-23T22:12:37.123Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":236,"q":192,"t":"2020-11-23T22:12:37.123Z"},{"v":236,"q":192,"t":"2020-11-23T22:12:37.123Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:37.6041105Z","EventEnqueuedUtcTime":"2020-11-23T22:12:37.4230000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":230,"q":192,"t":"2020-11-23T22:12:42.122Z"},{"v":230,"q":192,"t":"2020-11-23T22:12:42.122Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":235,"q":192,"t":"2020-11-23T22:12:42.122Z"},{"v":235,"q":192,"t":"2020-11-23T22:12:42.122Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:42.5297917Z","EventEnqueuedUtcTime":"2020-11-23T22:12:42.3300000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":240,"q":192,"t":"2020-11-23T22:12:47.122Z"},{"v":240,"q":192,"t":"2020-11-23T22:12:47.122Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":235,"q":192,"t":"2020-11-23T22:12:47.122Z"},{"v":235,"q":192,"t":"2020-11-23T22:12:47.122Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:47.3512855Z","EventEnqueuedUtcTime":"2020-11-23T22:12:47.2680000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":250,"q":192,"t":"2020-11-23T22:12:52.122Z"},{"v":250,"q":192,"t":"2020-11-23T22:12:52.122Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":234,"q":192,"t":"2020-11-23T22:12:52.122Z"},{"v":234,"q":192,"t":"2020-11-23T22:12:52.122Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:52.5081189Z","EventEnqueuedUtcTime":"2020-11-23T22:12:52.4090000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":260,"q":192,"t":"2020-11-23T22:12:57.121Z"},{"v":260,"q":192,"t":"2020-11-23T22:12:57.121Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":233,"q":192,"t":"2020-11-23T22:12:57.121Z"},{"v":233,"q":192,"t":"2020-11-23T22:12:57.121Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:12:57.4384939Z","EventEnqueuedUtcTime":"2020-11-23T22:12:57.3310000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
{"gatewayData":[{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.counter1","vqts":[{"v":270,"q":192,"t":"2020-11-23T22:13:02.121Z"},{"v":270,"q":192,"t":"2020-11-23T22:13:02.121Z"}],"mimeType":"x-ra/cip/dint"},{"tag_id":"ra-cip-value://cip-1-test/10.97.1.68/Program:MainProgram.Counting1.ACC","vqts":[{"v":232,"q":192,"t":"2020-11-23T22:13:02.121Z"},{"v":232,"q":192,"t":"2020-11-23T22:13:02.121Z"}],"mimeType":"x-ra/cip/dint"}],"deviceid":null,"EventProcessedUtcTime":"2020-11-23T22:13:02.2600743Z","EventEnqueuedUtcTime":"2020-11-23T22:13:02.2210000Z","ConnectionDeviceId":"joezplc","EnqueuedTime":null}
```

## Next Steps

## Parse the output JSON using Spark

- Here we are using Azure Databricks to parse the JSON output
- First create Azure databricks workspace
- Create a key vault to store the key for storage
- create a secret and store the key
- Create a cluster in databricks

## Code to load the data now

- the format is shown above.
- load the sql driver or later use

```
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
```

- import the libraries

```
import org.apache.spark.sql.types._                         // include the Spark Types to define our schema
import org.apache.spark.sql.functions._   
```

- Load the storage key from keyvault

```
val accbbstorekey = dbutils.secrets.get(scope = "scopename", key = "keyname")
```

- Now configure the storage to access

```
spark.conf.set(
  "fs.azure.account.key.accountname.blob.core.windows.net",
  accbbstorekey)
```

- Specify the path to read JSON files

```
val jsonpath = "wasbs://containername@accountname.blob.core.windows.net/2020/11/23/0_e7d51dfce4664331bdd4d984b91bdaed_1.json"
```

- Create the schema

```
import org.apache.spark.sql.types._

val schema = new StructType()
  .add("ConnectionDeviceId", StringType)                               // data center where data was posted to Kafka cluster
  .add("gatewayData",                                          // info about the source of alarm
    ArrayType(                                              // define this as a Map(Key->value)
      new StructType()
      .add("mimeType", StringType)
      .add("tag_id", StringType)
      .add("vqts", 
           ArrayType(
                new StructType()
                .add("q", DoubleType)
                .add("t", StringType)
                .add("v", DoubleType)
           )
        )
      )
    )
```

- now read into dataframe

```
val df = spark                  // spark session 
.read                           // get DataFrameReader
.schema(schema)                 // use the defined schema above and read format as JSON
.json(jsonpath)
```

- Display Device information, tags and corresponding array of values

```
display(df.select("ConnectionDeviceId", "gatewayData.tag_id", "gatewayData.vqts"))
```

- sample processed data

```
ConnectionDeviceId,tag_id,vqts
null,null,null
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1""]","[[{""q"":192,""t"":""2020-11-23T21:34:59.048Z"",""v"":705},{""q"":192,""t"":""2020-11-23T21:35:01.047Z"",""v"":709}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1""]","[[{""q"":192,""t"":""2020-11-23T21:35:03.047Z"",""v"":713}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T21:35:05.097Z"",""v"":717},{""q"":192,""t"":""2020-11-23T21:35:07.110Z"",""v"":721},{""q"":192,""t"":""2020-11-23T21:35:09.124Z"",""v"":725},{""q"":192,""t"":""2020-11-23T21:35:11.124Z"",""v"":729},{""q"":192,""t"":""2020-11-23T21:35:13.125Z"",""v"":733}],[{""q"":192,""t"":""2020-11-23T21:35:05.097Z"",""v"":69},{""q"":192,""t"":""2020-11-23T21:35:07.110Z"",""v"":83},{""q"":192,""t"":""2020-11-23T21:35:09.124Z"",""v"":97},{""q"":192,""t"":""2020-11-23T21:35:13.125Z"",""v"":98}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T21:35:15.149Z"",""v"":737},{""q"":192,""t"":""2020-11-23T21:35:17.159Z"",""v"":741},{""q"":192,""t"":""2020-11-23T21:35:19.172Z"",""v"":745},{""q"":192,""t"":""2020-11-23T21:35:21.176Z"",""v"":749},{""q"":192,""t"":""2020-11-23T21:35:23.185Z"",""v"":753}],[{""q"":192,""t"":""2020-11-23T21:35:15.149Z"",""v"":122},{""q"":192,""t"":""2020-11-23T21:35:17.159Z"",""v"":132},{""q"":192,""t"":""2020-11-23T21:35:19.172Z"",""v"":144},{""q"":192,""t"":""2020-11-23T21:35:21.176Z"",""v"":149},{""q"":192,""t"":""2020-11-23T21:35:23.185Z"",""v"":158}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T21:35:25.185Z"",""v"":757},{""q"":192,""t"":""2020-11-23T21:35:27.187Z"",""v"":761},{""q"":192,""t"":""2020-11-23T21:35:29.186Z"",""v"":765},{""q"":192,""t"":""2020-11-23T21:35:31.200Z"",""v"":769},{""q"":192,""t"":""2020-11-23T21:35:33.200Z"",""v"":773}],[{""q"":192,""t"":""2020-11-23T21:35:27.187Z"",""v"":159},{""q"":192,""t"":""2020-11-23T21:35:31.200Z"",""v"":173},{""q"":192,""t"":""2020-11-23T21:35:33.200Z"",""v"":172}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T21:35:35.209Z"",""v"":777},{""q"":192,""t"":""2020-11-23T21:35:37.215Z"",""v"":781},{""q"":192,""t"":""2020-11-23T21:35:39.215Z"",""v"":785},{""q"":192,""t"":""2020-11-23T21:35:41.216Z"",""v"":789},{""q"":192,""t"":""2020-11-23T21:35:43.231Z"",""v"":793}],[{""q"":192,""t"":""2020-11-23T21:35:35.209Z"",""v"":182},{""q"":192,""t"":""2020-11-23T21:35:37.215Z"",""v"":188},{""q"":192,""t"":""2020-11-23T21:35:39.215Z"",""v"":187},{""q"":192,""t"":""2020-11-23T21:35:41.216Z"",""v"":188},{""q"":192,""t"":""2020-11-23T21:35:43.231Z"",""v"":204}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T21:35:45.247Z"",""v"":797}],[{""q"":192,""t"":""2020-11-23T21:35:45.247Z"",""v"":219}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T21:35:47.245Z"",""v"":801},{""q"":192,""t"":""2020-11-23T21:35:49.246Z"",""v"":805},{""q"":192,""t"":""2020-11-23T21:35:51.262Z"",""v"":809},{""q"":192,""t"":""2020-11-23T21:35:53.277Z"",""v"":813},{""q"":192,""t"":""2020-11-23T21:35:55.291Z"",""v"":817},{""q"":192,""t"":""2020-11-23T21:35:57.291Z"",""v"":821},{""q"":192,""t"":""2020-11-23T21:35:59.291Z"",""v"":825}],[{""q"":192,""t"":""2020-11-23T21:35:47.245Z"",""v"":218},{""q"":192,""t"":""2020-11-23T21:35:49.246Z"",""v"":219},{""q"":192,""t"":""2020-11-23T21:35:51.262Z"",""v"":235},{""q"":192,""t"":""2020-11-23T21:35:53.277Z"",""v"":250},{""q"":192,""t"":""2020-11-23T21:35:55.291Z"",""v"":264},{""q"":192,""t"":""2020-11-23T21:35:59.291Z"",""v"":263}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:50:15.711Z"",""v"":4737},{""q"":192,""t"":""2020-11-23T22:50:17.712Z"",""v"":4741},{""q"":192,""t"":""2020-11-23T22:50:19.711Z"",""v"":4745},{""q"":192,""t"":""2020-11-23T22:50:21.711Z"",""v"":4749},{""q"":192,""t"":""2020-11-23T22:50:23.710Z"",""v"":4753}],[{""q"":192,""t"":""2020-11-23T22:50:21.711Z"",""v"":399}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:50:25.710Z"",""v"":4757},{""q"":192,""t"":""2020-11-23T22:50:27.710Z"",""v"":4761},{""q"":192,""t"":""2020-11-23T22:50:29.709Z"",""v"":4765},{""q"":192,""t"":""2020-11-23T22:50:31.709Z"",""v"":4769},{""q"":192,""t"":""2020-11-23T22:50:33.709Z"",""v"":4773}],[{""q"":192,""t"":""2020-11-23T22:50:29.709Z"",""v"":398},{""q"":192,""t"":""2020-11-23T22:50:31.709Z"",""v"":399},{""q"":192,""t"":""2020-11-23T22:50:33.709Z"",""v"":398}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:50:35.708Z"",""v"":4777},{""q"":192,""t"":""2020-11-23T22:50:37.708Z"",""v"":4781},{""q"":192,""t"":""2020-11-23T22:50:39.708Z"",""v"":4785},{""q"":192,""t"":""2020-11-23T22:50:41.707Z"",""v"":4789},{""q"":192,""t"":""2020-11-23T22:50:43.707Z"",""v"":4793}],[{""q"":192,""t"":""2020-11-23T22:50:39.708Z"",""v"":397}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:50:45.707Z"",""v"":4797},{""q"":192,""t"":""2020-11-23T22:50:47.707Z"",""v"":4801},{""q"":192,""t"":""2020-11-23T22:50:49.706Z"",""v"":4805},{""q"":192,""t"":""2020-11-23T22:50:51.706Z"",""v"":4809},{""q"":192,""t"":""2020-11-23T22:50:53.706Z"",""v"":4813}],[{""q"":192,""t"":""2020-11-23T22:50:49.706Z"",""v"":396}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:50:55.705Z"",""v"":4817},{""q"":192,""t"":""2020-11-23T22:50:57.705Z"",""v"":4821},{""q"":192,""t"":""2020-11-23T22:50:59.705Z"",""v"":4825},{""q"":192,""t"":""2020-11-23T22:51:01.704Z"",""v"":4829},{""q"":192,""t"":""2020-11-23T22:51:03.704Z"",""v"":4833}],[{""q"":192,""t"":""2020-11-23T22:50:57.705Z"",""v"":395}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:51:05.704Z"",""v"":4837},{""q"":192,""t"":""2020-11-23T22:51:07.704Z"",""v"":4841},{""q"":192,""t"":""2020-11-23T22:51:09.703Z"",""v"":4845},{""q"":192,""t"":""2020-11-23T22:51:11.703Z"",""v"":4849},{""q"":192,""t"":""2020-11-23T22:51:13.703Z"",""v"":4853}],[{""q"":192,""t"":""2020-11-23T22:51:07.704Z"",""v"":394}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:51:15.702Z"",""v"":4857},{""q"":192,""t"":""2020-11-23T22:51:17.702Z"",""v"":4861},{""q"":192,""t"":""2020-11-23T22:51:19.702Z"",""v"":4865},{""q"":192,""t"":""2020-11-23T22:51:21.702Z"",""v"":4869},{""q"":192,""t"":""2020-11-23T22:51:23.701Z"",""v"":4873}],[{""q"":192,""t"":""2020-11-23T22:51:17.702Z"",""v"":393}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:51:25.701Z"",""v"":4877},{""q"":192,""t"":""2020-11-23T22:51:27.700Z"",""v"":4881},{""q"":192,""t"":""2020-11-23T22:51:29.700Z"",""v"":4885},{""q"":192,""t"":""2020-11-23T22:51:31.700Z"",""v"":4889},{""q"":192,""t"":""2020-11-23T22:51:33.700Z"",""v"":4893}],[{""q"":192,""t"":""2020-11-23T22:51:27.700Z"",""v"":392}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:51:35.700Z"",""v"":4897},{""q"":192,""t"":""2020-11-23T22:51:37.699Z"",""v"":4901},{""q"":192,""t"":""2020-11-23T22:51:39.699Z"",""v"":4905},{""q"":192,""t"":""2020-11-23T22:51:41.698Z"",""v"":4909},{""q"":192,""t"":""2020-11-23T22:51:43.698Z"",""v"":4913}],[{""q"":192,""t"":""2020-11-23T22:51:37.699Z"",""v"":391}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1"",""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.Counting1.ACC""]","[[{""q"":192,""t"":""2020-11-23T22:51:45.698Z"",""v"":4917},{""q"":192,""t"":""2020-11-23T22:51:47.697Z"",""v"":4921},{""q"":192,""t"":""2020-11-23T22:51:49.698Z"",""v"":4925},{""q"":192,""t"":""2020-11-23T22:51:51.697Z"",""v"":4929},{""q"":192,""t"":""2020-11-23T22:51:53.697Z"",""v"":4933}],[{""q"":192,""t"":""2020-11-23T22:51:45.698Z"",""v"":390},{""q"":192,""t"":""2020-11-23T22:51:49.698Z"",""v"":391},{""q"":192,""t"":""2020-11-23T22:51:51.697Z"",""v"":390},{""q"":192,""t"":""2020-11-23T22:51:53.697Z"",""v"":389}]]"
joezlogix,"[""ra-cip-value://driver-cip/10.97.1.68/Program:MainProgram.counter1""]","[[{""q"":192,""t"":""2020-11-23T22:51:55.696Z"",""v"":4937},{""q"":192,""t"":""2020-11-23T22:51:57.696Z"",""v"":4941},{""q"":192,""t"":""2020-11-23T22:51:59.696Z"",""v"":4945},{""q"":192,""t"":""2020-11-23T22:52:01.695Z"",""v"":4949},{""q"":192,""t"":""2020-11-23T22:52:03.696Z"",""v"":4953}]]"
```

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/adb1.jpg "Architecture")

- More to come ...