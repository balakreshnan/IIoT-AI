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
            StructField("tag", ArrayType(StructType(Array(
              StructField("SourceTimestamp", StringType, true),
              StructField("Value", LongType, true)
              )
            )              
            ))
        )
        )))
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
        StructField("smKey", ArrayType(StructType(List(
          StructField("http://microsoft.com/Opc/OpcPlc/#s=FastUInt1", StructType(Array(
             StructField("SourceTimestamp", StringType, true),
             StructField("Value", LongType, true)
          )))
        )
        )))
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
        StructField("smKey", StructType(Array(
          StructField("http://microsoft.com/Opc/OpcPlc/#s=FastUInt1", StructType(Array(
             StructField("SourceTimestamp", StringType, true),
             StructField("Value", LongType, true)
          ))
        ),
        StructField("http://microsoft.com/Opc/OpcPlc/#s=FastUInt2", StructType(Array(
             StructField("SourceTimestamp", StringType, true),
             StructField("Value", LongType, true)
          ))
        )
        )))
      )
    )

// COMMAND ----------

val df1 = spark.read.option("multiline", "true").schema(schema).json(jsonpath)
//val df1 = spark.read.schema(ScalaReflection.schemaFor[KeywordData].dataType.asInstanceOf[StructType]).json(jsonpath).select(col("smKey"))

// COMMAND ----------

df1.printSchema

// COMMAND ----------

display(df1)

// COMMAND ----------

df1.printSchema

// COMMAND ----------

display(df1.select($"smKey.*"))

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

// COMMAND ----------

display(df.select($"smKey.*"))

// COMMAND ----------

val dfexpand = df.select($"ConnectionDeviceId", $"EnqueuedTime", $"EventEnqueuedUtcTime", $"EventProcessedUtcTime", $"deviceid", $"smKey.*")

// COMMAND ----------

display(dfexpand)

// COMMAND ----------

display(df)

// COMMAND ----------

val df10 = df.take(10)

// COMMAND ----------

display(df10)

// COMMAND ----------

import spark.implicits._
import spark.sql
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

// COMMAND ----------

df.createGlobalTempView("iiot")

// COMMAND ----------

spark.sql("SELECT * FROM global_temp.iiot limit 10").show()

// COMMAND ----------

val df10 = spark.sql("SELECT * FROM global_temp.iiot limit 10")

// COMMAND ----------

display(df10)

// COMMAND ----------

df10.write.mode(SaveMode.Overwrite).json("wasbs://opcuaincoming@iitostore.blob.core.windows.net/test.json")

// COMMAND ----------

df.select(explode(df("smKey"))).alias("tag").collect()

// COMMAND ----------

val dfexp = df.select($"ConnectionDeviceId", $"EnqueuedTime", $"EventEnqueuedUtcTime", $"EventProcessedUtcTime", $"deviceid", $"smKey.*")

// COMMAND ----------

display(dfexp)

// COMMAND ----------

dfexp.collect().foreach(row => row.toSeq.foreach(col => println(col)))

// COMMAND ----------

dfexp.createOrReplaceTempView("iiot")
val sqlDF = spark.sql("SELECT * FROM iiot")

// COMMAND ----------

sqlDF.foreach { row => 
           row.toSeq.foreach{col => println(col) }
    }

// COMMAND ----------

// MAGIC %sql
// MAGIC DESCRIBE iiot

// COMMAND ----------

// MAGIC %sql
// MAGIC select * from iiot

// COMMAND ----------

import org.apache.spark.sql.Column

// COMMAND ----------

dfexp.columns

// COMMAND ----------

dfexp.collect().foreach(row => row.toSeq.foreach(col => println("Field Name " + ":" + col)))

// COMMAND ----------

dfexp.columns.foreach( c => println(" - " + c))