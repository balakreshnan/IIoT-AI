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

val dfexp = gwdata.select($"ConnectionDeviceId", $"EnqueuedTime", $"EventEnqueuedUtcTime", $"EventProcessedUtcTime", $"deviceid", $"gatewayData.*")

// COMMAND ----------

display(dfexp)