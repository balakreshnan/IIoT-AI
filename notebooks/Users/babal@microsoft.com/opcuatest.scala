// Databricks notebook source
spark.conf.set(
  "fs.azure.account.key.iitostore.blob.core.windows.net",
  "BIro2yOAwGreaqhM3Znfh3V1A0JdCeaHnEq3cEpG+smtYDcvO/k/rykqg3JOqU6S5g2P8foalYWxAt6KWI/AIQ==")

// COMMAND ----------

val jsonpath = "wasbs://opcuaincoming@iitostore.blob.core.windows.net/2020/09/04"

// COMMAND ----------

val df = spark.read.option("multiline", "true").json(jsonpath)

// COMMAND ----------

display(df)

// COMMAND ----------

df.show()

// COMMAND ----------

df[["Messages"]]

// COMMAND ----------



// COMMAND ----------

df.printSchema

// COMMAND ----------

import org.apache.spark.sql.types._                         // include the Spark Types to define our schema
import org.apache.spark.sql.functions._   

// COMMAND ----------

val jsDF = df.select($"EventEnqueuedUtcTime", $"IoTHub.ConnectionDeviceId")

// COMMAND ----------

display(jsDF)

// COMMAND ----------

val jsDF = df.select($"EventEnqueuedUtcTime", $"IoTHub.ConnectionDeviceId", $"Messages.Payload")

// COMMAND ----------

display(jsDF)

// COMMAND ----------

val df1 = jsDF.select($"Payload".getItem(0).alias("smoke_alarms"))

// COMMAND ----------

display(df1)

// COMMAND ----------

val df1 = jsDF.select(explode($"Payload"))

// COMMAND ----------

display(df1)

// COMMAND ----------

df1.printSchema

// COMMAND ----------

df1.select.foreach(println)

// COMMAND ----------

val df1 = jsDF.select(col("Payload"))

// COMMAND ----------

display(df)

// COMMAND ----------

df.printSchema

// COMMAND ----------

display(df['Messages'])

// COMMAND ----------

val opcschema = """StructType(
  StructField(DataSetClassId,StringType,true),
  StructField(EventEnqueuedUtcTime,StringType,true),
  StructField(EventProcessedUtcTime,StringType,true),
  StructField(IoTHub, StructType(
      StructField(ConnectionDeviceGenerationId,StringType,true),
      StructField(ConnectionDeviceId,StringType,true),
      StructField(CorrelationId,StringType,true),
      StructField(EnqueuedTime,StringType,true),
      StructField(MessageId,StringType,true)
    )    
  )
  StructField(MessageId,StringType,true),
  StructField(MessageType,StringType,true),
  StructField(Messages, ArrayType(
    StructType(
        StructField(DataSetWriterId,StringType,true),
        StructField(MetaDataVersion,StructType(
            StructField(MajorVersion,LongType,true),
            StructField(MinorVersion,LongType,true)
          ),true),
        StructField(Payload,StructType(
            StructField(tagname,StructType(
                StructField(SourceTimestamp,StringType,true),
                StructField(MajorVersion,LongType,true)
              ),true)            
          ),true),
      )),true)
  StructField(PartitionId,LongType,true),
  StructField(PublisherId,StringType,true),
)"""

// COMMAND ----------

val df2 = spark.read.option("multiline", "true").option("inferSchema", "true").option("schema", "opcschema").json(jsonpath)

// COMMAND ----------

display(df2)

// COMMAND ----------

df2.printSchema