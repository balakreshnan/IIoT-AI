# Connect to Allen Bradly PLC using Azure IoT Edge OPC UA

## Architecture

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/2080abplc.jpg "Architecture")

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

- Run the stream analytics job
- Validate and see if the data is written in blob storage.
- More to come...