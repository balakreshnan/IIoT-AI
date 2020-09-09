// Databricks notebook source
spark.conf.set(
  "fs.azure.account.key.iitostore.blob.core.windows.net",
  "BIro2yOAwGreaqhM3Znfh3V1A0JdCeaHnEq3cEpG+smtYDcvO/k/rykqg3JOqU6S5g2P8foalYWxAt6KWI/AIQ==")

// COMMAND ----------

val jsonpath = "wasbs://opcuaincoming@iitostore.blob.core.windows.net/2020/09/08"

// COMMAND ----------

import org.apache.spark.sql.types._                         // include the Spark Types to define our schema
import org.apache.spark.sql.functions._   
import org.apache.spark.sql.functions._

// COMMAND ----------

val jsonSchema = new StructType()
        .add("ConnectionDeviceId", StringType)
        .add("EnqueuedTime", StringType)
        .add("EventEnqueuedUtcTime",StringType)
        .add("EventProcessedUtcTime", StringType)
        .add("deviceid", StringType)
        .add("smKey", Array(
         new StructType()
          .add("SourceTimestamp", StringType)
          .add("Value", DoubleType)
        ))

// COMMAND ----------

val schema = StructType(
      Array(
        StructField("ConnectionDeviceId", StringType),
        StructField("EnqueuedTime", StringType),
        StructField("EventEnqueuedUtcTime", StringType),
        StructField("EventProcessedUtcTime", StringType),
        StructField("deviceid", StringType),
        StructField("smKey", ArrayType(StructType(Array(
          StructField("SourceTimestamp", StringType),
          StructField("Value", DoubleType)
        ))))
      )
    )

// COMMAND ----------

val schema = StructType(
      Array(
        StructField("ConnectionDeviceId", StringType),
        StructField("EnqueuedTime", StringType),
        StructField("EventEnqueuedUtcTime", StringType),
        StructField("EventProcessedUtcTime", StringType),
        StructField("deviceid", StringType),
        StructField("smKey", StructType(
        List(
          StructField("SourceTimestamp", StringType, true),
          StructField("Value", DoubleType, true)
        )
        ))
      )
    )

// COMMAND ----------

case class KeywordData (keywordsByCode: Map[String, sensor])

// COMMAND ----------

case class sensor(code: Map[String, Map[String, Integer]])

// COMMAND ----------

import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.catalyst.ScalaReflection.universe.TypeTag

// COMMAND ----------

val df = spark.read.json(jsonpath)

// COMMAND ----------

display(df)

// COMMAND ----------

df.printSchema

// COMMAND ----------

val schema = StructType(
      Array(
        StructField("ConnectionDeviceId", StringType),
        StructField("EnqueuedTime", StringType),
        StructField("EventEnqueuedUtcTime", StringType),
        StructField("EventProcessedUtcTime", StringType),
        StructField("deviceid", StringType),
        StructField("smKey", ArrayType(StructType(Array(
          StructField("tag", StructType(
            StructField("SourceTimestamp", StringType, true),
            StructField("Value", DoubleType, true)
          )))
        )))
      )
    )

// COMMAND ----------

val df1 = spark.read.option("multiline", "true").schema(schema).json(jsonpath)
//val df1 = spark.read.schema(ScalaReflection.schemaFor[KeywordData].dataType.asInstanceOf[StructType]).json(jsonpath).select(col("smKey"))

// COMMAND ----------

display(df1)

// COMMAND ----------

df.printSchema

// COMMAND ----------

df.select($"smKey"(0))

// COMMAND ----------

import org.apache.spark.sql.functions._

// COMMAND ----------

display(df.select(col("smKey")))

// COMMAND ----------

val df1 = df.select(col("smKey"))

// COMMAND ----------

display(df1)

// COMMAND ----------

val flattenDF = df1.select($"smKey.*")
display(flattenDF)

// COMMAND ----------

flattenDF.printSchema