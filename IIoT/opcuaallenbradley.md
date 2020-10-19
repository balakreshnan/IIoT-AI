# Connect to Allen Bradly PLC using Azure IoT Edge OPC UA

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

- Using PLC to Azure Cloud

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/2080abplc1.jpg "Architecture")

- Using Control Logix to Azure Cloud. This option allows to control multiple PLC in Customer Site.

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/5370abcontrollogix1.jpg "Architecture")

## Steps to Connect

- Here is the link
- https://github.com/Azure/Industrial-IoT/blob/master/docs/tutorials/tut-iiot-cost-estimation.md
- First get a PLC or OPC simulator
- I am using Rasperry Pi
- Create Azure IoT hub
- Create a Iot edge devices
- Log into Raspberry PI
- Update and install Iot Edge
- https://docs.microsoft.com/en-us/azure/iot-edge/how-to-install-iot-edge-linux
- Now there should be 2 modules - base modules
- Let's now install the opc publisher module.
- Go to iot edge devices 
- Add a module from market place
- Select OPCTwin
- Select OPCPublisher
- COnfigure the OPCPublisher
- Update the container options

```
{
  "Cmd": [
    "publisher",
    "--pf=/appdata/pn.json",
    "--di=60",
    "--to",
    "--aa",
    "--si=10",
    "--bs=100",
    "--mm=PubSub",
    "--me=Json"
  ],
  "HostConfig": {
    "Binds": [
      "/var/iiotedge:/appdata"
    ]
  }
}
```

- Create a folder in /var/iiotedge
- create a published nodes configuration

```
[
  {
    "EndpointUrl": "opc.tcp://950aa2a7fb7a:50000",
    "UseSecurity": false,
    "OpcNodes": [
      { "Id": "ns=2;s=SlowUInt1", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt2", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt3", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt4", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt5", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt6", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt7", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt8", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt9", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=SlowUInt10", "OpcPublishingInterval": 10000 },
      { "Id": "ns=2;s=FastUInt1" },
      { "Id": "ns=2;s=FastUInt2" },
      { "Id": "ns=2;s=FastUInt3" },
      { "Id": "ns=2;s=FastUInt4" },
      { "Id": "ns=2;s=FastUInt5" },
      { "Id": "ns=2;s=FastUInt6" },
      { "Id": "ns=2;s=FastUInt7" },
      { "Id": "ns=2;s=FastUInt8" },
      { "Id": "ns=2;s=FastUInt9" },
      { "Id": "ns=2;s=FastUInt10" },
      { "Id": "ns=2;s=FastUInt11" },
      { "Id": "ns=2;s=FastUInt12" },
      { "Id": "ns=2;s=FastUInt13" },
      { "Id": "ns=2;s=FastUInt14" },
      { "Id": "ns=2;s=FastUInt15" },
      { "Id": "ns=2;s=FastUInt16" },
      { "Id": "ns=2;s=FastUInt17" },
      { "Id": "ns=2;s=FastUInt18" },
      { "Id": "ns=2;s=FastUInt19" },
      { "Id": "ns=2;s=FastUInt20" },
      { "Id": "ns=2;s=FastUInt21" },
      { "Id": "ns=2;s=FastUInt22" },
      { "Id": "ns=2;s=FastUInt23" },
      { "Id": "ns=2;s=FastUInt24" },
      { "Id": "ns=2;s=FastUInt25" },
      { "Id": "ns=2;s=FastUInt26" },
      { "Id": "ns=2;s=FastUInt27" },
      { "Id": "ns=2;s=FastUInt28" },
      { "Id": "ns=2;s=FastUInt29" },
      { "Id": "ns=2;s=FastUInt30" },
      { "Id": "ns=2;s=FastUInt31" },
      { "Id": "ns=2;s=FastUInt32" },
      { "Id": "ns=2;s=FastUInt33" },
      { "Id": "ns=2;s=FastUInt34" },
      { "Id": "ns=2;s=FastUInt35" },
      { "Id": "ns=2;s=FastUInt36" },
      { "Id": "ns=2;s=FastUInt37" },
      { "Id": "ns=2;s=FastUInt38" },
      { "Id": "ns=2;s=FastUInt39" },
      { "Id": "ns=2;s=FastUInt40" },
      { "Id": "ns=2;s=FastUInt41" },
      { "Id": "ns=2;s=FastUInt42" },
      { "Id": "ns=2;s=FastUInt43" },
      { "Id": "ns=2;s=FastUInt44" },
      { "Id": "ns=2;s=FastUInt45" },
      { "Id": "ns=2;s=FastUInt46" },
      { "Id": "ns=2;s=FastUInt47" },
      { "Id": "ns=2;s=FastUInt48" },
      { "Id": "ns=2;s=FastUInt49" },
      { "Id": "ns=2;s=FastUInt50" },
      { "Id": "ns=2;s=FastUInt51" },
      { "Id": "ns=2;s=FastUInt52" },
      { "Id": "ns=2;s=FastUInt53" },
      { "Id": "ns=2;s=FastUInt54" },
      { "Id": "ns=2;s=FastUInt55" },
      { "Id": "ns=2;s=FastUInt56" },
      { "Id": "ns=2;s=FastUInt57" },
      { "Id": "ns=2;s=FastUInt58" },
      { "Id": "ns=2;s=FastUInt59" },
      { "Id": "ns=2;s=FastUInt60" },
      { "Id": "ns=2;s=FastUInt61" },
      { "Id": "ns=2;s=FastUInt62" },
      { "Id": "ns=2;s=FastUInt63" },
      { "Id": "ns=2;s=FastUInt64" },
      { "Id": "ns=2;s=FastUInt65" },
      { "Id": "ns=2;s=FastUInt66" },
      { "Id": "ns=2;s=FastUInt67" },
      { "Id": "ns=2;s=FastUInt68" },
      { "Id": "ns=2;s=FastUInt69" },
      { "Id": "ns=2;s=FastUInt70" },
      { "Id": "ns=2;s=FastUInt71" },
      { "Id": "ns=2;s=FastUInt72" },
      { "Id": "ns=2;s=FastUInt73" },
      { "Id": "ns=2;s=FastUInt74" },
      { "Id": "ns=2;s=FastUInt75" },
      { "Id": "ns=2;s=FastUInt76" },
      { "Id": "ns=2;s=FastUInt77" },
      { "Id": "ns=2;s=FastUInt78" },
      { "Id": "ns=2;s=FastUInt79" },
      { "Id": "ns=2;s=FastUInt80" },
      { "Id": "ns=2;s=FastUInt81" },
      { "Id": "ns=2;s=FastUInt82" },
      { "Id": "ns=2;s=FastUInt83" },
      { "Id": "ns=2;s=FastUInt84" },
      { "Id": "ns=2;s=FastUInt85" },
      { "Id": "ns=2;s=FastUInt86" },
      { "Id": "ns=2;s=FastUInt87" },
      { "Id": "ns=2;s=FastUInt88" },
      { "Id": "ns=2;s=FastUInt89" },
      { "Id": "ns=2;s=FastUInt90" },
      { "Id": "ns=2;s=FastUInt91" },
      { "Id": "ns=2;s=FastUInt92" },
      { "Id": "ns=2;s=FastUInt93" },
      { "Id": "ns=2;s=FastUInt94" },
      { "Id": "ns=2;s=FastUInt95" },
      { "Id": "ns=2;s=FastUInt96" },
      { "Id": "ns=2;s=FastUInt97" },
      { "Id": "ns=2;s=FastUInt98" },
      { "Id": "ns=2;s=FastUInt99" },
      { "Id": "ns=2;s=FastUInt100" }
    ]
  }
]
```

- The above is sample of pn.json that we pass along which has OPC server URI
- Then the tags and frequency on how to retrieve it

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcua.jpg "Architecture")

## Simulator Test for OPC PLC

- to test a opc simulator to test the publisher we can use
- Docker image is available
- https://github.com/Azure-Samples/iot-edge-opc-plc
- now pull the version

```
Docker pull mcr.microsoft.com/iotedge/opc-plc:1.1.6
```
- run the above command and wait for image to download
- now time to execute the simulator

```
docker run --rm -it -p 50000:50000 -p 8080:8080 --name opcplc mcr.microsoft.com/iotedge/opc-plc:1.1.6 --pn=50000 --at X509Store --autoaccept --nospikes --nodips --nopostrend --nonegtrend --nodatavalues --sph --sn=10 --sr=10 --st=uint --fn=100 --fr=1 --ft=uint
```

- -sn 10 <--- these are 10 slow nodes that change value every 10 seconds (sr)
- --fn 100 means 100 fast values, changing every 1 s (fr)
- So you have 100 nodes changing every sec + 10 nodes that change every 10 seconds
- The avg should be more than 90/s, at least 100, which are the fast nodes

## Change Publisher configuration

- Go to Publisher pn.json file and change the OPC UA IP which in this case the above simulator VM (Host) IP
- Restart the iot edge
- Display 

```
sudo docker logs OPCPublisher
```

- Should display log and make sure everything is connected and sending data

```
  DIAGNOSTICS INFORMATION for          : opc.tcp://xxx.xxx.x.xx:50000_50372a287f98a7d166661d61eeecf23e444ae4ed
  # Ingestion duration                 :    00:03:57:55 (dd:hh:mm:ss)
  # Ingress DataChanges (from OPC)     :         15,559 (1.09/s)
  # Ingress ValueChanges (from OPC)    :      1,427,364 (99.99/s)
  # Ingress BatchBlock buffer size     :              0
  # Encoding Block input/output size   :              0 | 0
  # Encoder Notifications processed    :         15,558
  # Encoder Notifications dropped      :              0
  # Encoder IoT Messages processed     :          1,427
  # Encoder avg Notifications/Message  :             11
  # Encoder avg IoT Message body size  :        115,350 (44%)
  # Encoder avg IoT Chunk (4 KB) usage :           28.2
  # Estimated IoT Chunks (4 KB) per day:        250,467
  # Outgress Batch Block buffer size   :              0
  # Outgress input buffer count        :              0
  # Outgress IoT message count         :          1,427 (0.1/s)
  # Connection retries                 :              0
```

- Now you should see the data going to IoT Hub
- Create Stream Analytics resource
- Create a Blob Storage Account
- Create a empty container to store the data
- Create Stream Analytics Jobs
- Create the input as iot hub
- create the output as blob storage
- Store as JSON to avoid dederialization errors
- Query is

```
select * into outputblob from input
```

```
WITH DynamicCTE AS 
(
    SELECT   
        i.PublisherId as deviceid,	
        i.EventProcessedUtcTime as EventProcessedUtcTime,
        i.EventEnqueuedUtcTime as EventEnqueuedUtcTime,
        i.IoTHub.ConnectionDeviceId as ConnectionDeviceId,
        i.IoTHub.EnqueuedTime as EnqueuedTime,
        SensorMetadataRecords.ArrayValue.Payload as smKey,
        SensorMetadataRecords.ArrayValue.Payload as smValue
    FROM input i
    CROSS APPLY GetArrayElements(Messages) AS SensorMetadataRecords
)
select 
    deviceid,
    ConnectionDeviceId,
    EventProcessedUtcTime,
    EventEnqueuedUtcTime,
    EnqueuedTime,
    smKey,
    smValue
    from DynamicCTE
```

- Run the stream analytics job
- Validate and see if the data is written in blob storage.
- Above query writes one level falttened json file
- Now lets create azure databricks
- Create a dev cluster with default setting. i am using 3 nodes for now
- Create a scala notebook
- Lets write code to parse the JSON and pull details anad write to Azure database for testing
- Goal is parse all the tags and corresponding time and value
- Lets write scala code to parse the JSON

```
import org.apache.spark.sql.types._                         // include the Spark Types to define our schema
import org.apache.spark.sql.functions._   
import org.apache.spark.sql.functions._
import spark.implicits._
import spark.sql
import org.apache.spark.sql.{Row, SaveMode, SparkSession}
```

- Load sql drivers

```
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
```

- Configure the blob storage config to read the data

```
spark.conf.set(
  "fs.azure.account.key.xxxxxx.blob.core.windows.net",
  "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
```

- Now set the file path

```
val jsonpath = "wasbs://container@accountname.blob.core.windows.net/2020/09/08"
```

```
val df = spark.read.json(jsonpath)
display(df)
```

- now we are expanding the columns

```
val dfexp = df.select($"ConnectionDeviceId", $"EnqueuedTime", $"EventEnqueuedUtcTime", $"EventProcessedUtcTime", $"deviceid", $"smKey.*")
display(dfexp)
```

- display columns

```
dfexp.columns.foreach( c => println(" - " + c))
```

- Display the rows for troubleshooting

```
dfexp.collect().foreach(row => row.toSeq.foreach(col => println("Field Name " + ":" + col)))
```

- now configure jdbc url and connections string

```
val jdbcHostname = "servername.database.windows.net"
val jdbcPort = 1433
val jdbcDatabase = "dbname"

// Create the JDBC URL without passing in the user and password parameters.
val jdbcUrl = s"jdbc:sqlserver://${jdbcHostname}:${jdbcPort};database=${jdbcDatabase}"

// Create a Properties() object to hold the parameters.
import java.util.Properties
val connectionProperties = new Properties()

connectionProperties.put("user", s"username")
connectionProperties.put("password", s"password")
```

- Connect to sql

```
val driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
connectionProperties.setProperty("Driver", driverClass)
```

```
val opcusdata = spark.read.jdbc(jdbcUrl, "opcuadata", connectionProperties)
display(opcusdata)
```

- Create a class

```
import java.sql.Timestamp

case class opcusdata(deviceid: String, tagName: String, tagdata: String, tagTime: Timestamp, tagvalue: Double)
case class opcusdata1(deviceid: String, tagName: String, tagdata: String)
```

- create mutable list to store all the data

```
import scala.collection.mutable.ListBuffer
import scala.collection._

val list = mutable.MutableList[opcusdata]()
```

- Now loop Row and Column on each row and then create a record and save to Database.

```
val cols = dfexp.columns
val columncount = 0

dfexp.collect().foreach(row => 
                        {
                          val columncount = 0
                          val deviceid = row(0).toString
                          row.toSeq.zipWithIndex.foreach(col => 
                                            {
                                              //println(s"${col._1}" + ": " + col)
                                              val sourceTimestap = col._1
                                              val value = col._2
                                              //println(cols(value) + ": " + sourceTimestap)
                                              //val deviceid = "plcgateway";
                                              if(cols(value).startsWith("http://microsoft"))
                                              {
                                                if (sourceTimestap != null)
                                                {
                                                  val tagName = cols(value)
                                                  val tagdata = sourceTimestap
                                                  val tgdata = tagdata.toString.split(",")
                                                  //val spdata = split(tagdata, ",")
                                                  val tagTime = tgdata(0).replace("[","")
                                                  val tagValue = tgdata(1).replace("]","")

                                                  //val data = spark.createDataFrame((deviceid, cols(value), sourceTimestap))
                                                  //data.write.jdbc(jdbcUrl, "opcuadata", mode="append", connectionProperties)
                                                  //val opcusdata1 = new opcusdata1(deviceid, tagName, tagdata.toString)
                                                  val opcusdata1 = new opcusdata(deviceid, tagName, tagdata.toString, Timestamp.valueOf(tagTime.replace("T", " ").replace("Z","")), tagValue.toDouble)
                                                  val departmentsWithEmployeesSeq1 = Seq(opcusdata1)
                                                  val df1 = departmentsWithEmployeesSeq1.toDF()
                                                  list += opcusdata1
                                                  //df1.write.mode(SaveMode.Append).jdbc(jdbcUrl, "opcuadata",connectionProperties)
                                                  println(deviceid + ": " + tagName + ": " + tagdata + ":" + tagTime + ": " + tagValue )
                                                }
                                                
                                              }
                                              
                                              columncount + 1
                                            }                                   
                                           )
                                            
                          println("row completed")
                        })
```

- Above code pulls device id
- Then looks for columns starting with https://microsoft which is how the tag names are specified for simulator
- Then split the timesstamp and value
- update the list with values.
- now convert the list to dataframe

```
list.size
val dfall = list.toDF()
display(dfall)
```

- Now write to database

```
dfall.write.mode(SaveMode.Append).jdbc(jdbcUrl, "opcuadata", connectionProperties)
```

- Once completed log into Azure SQL run queries and validate if the data is loaded

- Here is the sample data should look like

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcuasqlraw.jpg "Architecture")

- More details on optimized load is coming soon.
