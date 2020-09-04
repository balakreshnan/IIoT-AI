# Connect to Allen Bradly PLC using Azure IoT Edge OPC UA 

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
    "--me=Uadp"
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
        "EndpointUrl": "opc.tcp://<SERVER>.westeurope.cloudapp.azure.com:49320",
        "UseSecurity": false,
        "OpcNodes": [
            {
                "Id": "ns=2;s=SIM.CH1.SIM_CH1_TAG1\\234754a-c63-b9601",
                "OpcSamplingInterval": 1000,
                "OpcPublishingInterval": 1000
            },
            {
                "Id": "ns=2;s=SIM.CH1.SIM_CH1_TAG19\\2347798-c63-02601",
                "OpcSamplingInterval": 1000,
                "OpcPublishingInterval": 1000
            }
        ]
    }
]
```

- The above is sample of pn.json that we pass along which has OPC server URI
- Then the tags and frequency on how to retrieve it

![alt text](https://github.com/balakreshnan/IIoT-AI/blob/master/IIoT/images/opcua.jpg "Architecture")
