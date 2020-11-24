// Databricks notebook source
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

// COMMAND ----------

import org.apache.spark.sql.types._                         // include the Spark Types to define our schema
import org.apache.spark.sql.functions._   
import org.apache.spark.sql.functions._

// COMMAND ----------

val accbbstorekey = dbutils.secrets.get(scope = "allsecrets", key = "opcbbstore")

// COMMAND ----------

spark.conf.set(
  "fs.azure.account.key.iitostore.blob.core.windows.net",
  accbbstorekey)

// COMMAND ----------

val jsonpath = "wasbs://opcuaincoming@iitostore.blob.core.windows.net/2020/11/23/0_e7d51dfce4664331bdd4d984b91bdaed_1.json"
//val jsonpath = "wasbs://opcuaincoming@iitostore.blob.core.windows.net/2020/11/24/0_77513b49a6264687a6965f0d26c64d75_1.json"
//val jsonpath = "wasbs://opcuaincoming@iitostore.blob.core.windows.net/2020/11/24/0_c751a98390934ca1b38e0853f510da29_1.json"

// COMMAND ----------

import org.apache.spark.sql.types._

val schema = new StructType()
  .add("ConnectionDeviceId", StringType)                               // data center where data was posted to Kafka cluster
  .add("EnqueuedTime", StringType)
  .add("EventEnqueuedUtcTime", StringType)
  .add("EventProcessedUtcTime", StringType)
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

// COMMAND ----------

val gwdata = spark.read.schema(schema).json(jsonpath)
display(gwdata)

// COMMAND ----------

gwdata.printSchema

// COMMAND ----------

val dfexp = gwdata.select($"ConnectionDeviceId", $"EnqueuedTime", $"EventEnqueuedUtcTime", $"EventProcessedUtcTime", $"deviceid", $"gatewayData")

// COMMAND ----------

display(dfexp)

// COMMAND ----------

dfexp.printSchema

// COMMAND ----------

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

// COMMAND ----------

val df = spark                  // spark session 
.read                           // get DataFrameReader
.schema(schema)                 // use the defined schema above and read format as JSON
.json(jsonpath) 

// COMMAND ----------

display(df.select("ConnectionDeviceId", "gatewayData.tag_id", "gatewayData.vqts"))

// COMMAND ----------

//display(df.select("ConnectionDeviceId", explode($"gatewayData.tag_id"), "gatewayData.vqts"))
df.select($"ConnectionDeviceId",posexplode($"gatewayData.tag_id")).show(false)

// COMMAND ----------

df.select($"ConnectionDeviceId", posexplode($"gatewayData.vqts")).show(false)