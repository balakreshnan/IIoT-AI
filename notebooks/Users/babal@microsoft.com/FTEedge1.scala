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

// COMMAND ----------

val gwdata = spark.read.json(jsonpath)
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

val jsDF = dfexp.select($"gatewayData", get_json_object($"gatewayData", "$.tag_id").alias("Tag_id"))

// COMMAND ----------

display(dfexp.select(to_json('gatewayData) as 'c))

// COMMAND ----------

display(dfexp.select(json_tuple('gatewayData, "tag_id") as 'c))