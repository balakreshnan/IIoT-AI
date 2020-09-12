// Databricks notebook source
import org.apache.spark.sql.types._                         // include the Spark Types to define our schema
import org.apache.spark.sql.functions._   
import org.apache.spark.sql.functions._

// COMMAND ----------

Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

// COMMAND ----------

import spark.implicits._
import spark.sql
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

// COMMAND ----------

spark.conf.set(
  "fs.azure.account.key.iitostore.blob.core.windows.net",
  "BIro2yOAwGreaqhM3Znfh3V1A0JdCeaHnEq3cEpG+smtYDcvO/k/rykqg3JOqU6S5g2P8foalYWxAt6KWI/AIQ==")

// COMMAND ----------

val jsonpath = "wasbs://opcuaincoming@iitostore.blob.core.windows.net/2020/09/08"

// COMMAND ----------

val df = spark.read.json(jsonpath)

// COMMAND ----------

display(df)

// COMMAND ----------

val dfexp = df.select($"ConnectionDeviceId", $"EnqueuedTime", $"EventEnqueuedUtcTime", $"EventProcessedUtcTime", $"deviceid", $"smKey.*")

// COMMAND ----------

display(dfexp)

// COMMAND ----------

dfexp.columns.foreach( c => println(" - " + c))

// COMMAND ----------

dfexp.collect().foreach(row => row.toSeq.foreach(col => println("Field Name " + ":" + col)))

// COMMAND ----------

val jdbcHostname = "opcuadatasvr.database.windows.net"
val jdbcPort = 1433
val jdbcDatabase = "opcuadata"

// Create the JDBC URL without passing in the user and password parameters.
val jdbcUrl = s"jdbc:sqlserver://${jdbcHostname}:${jdbcPort};database=${jdbcDatabase}"

// Create a Properties() object to hold the parameters.
import java.util.Properties
val connectionProperties = new Properties()

connectionProperties.put("user", s"sqladmin")
connectionProperties.put("password", s"Azure!2345678")

// COMMAND ----------

val driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
connectionProperties.setProperty("Driver", driverClass)

// COMMAND ----------

val opcusdata = spark.read.jdbc(jdbcUrl, "opcuadata", connectionProperties)

// COMMAND ----------

display(opcusdata)

// COMMAND ----------

opcusdata.toDF.createGlobalTempView("opcusdata")

// COMMAND ----------

spark.sql("SELECT * FROM global_temp.opcusdata limit 10").show()

// COMMAND ----------

opcusdata.printSchema

// COMMAND ----------

import java.sql.Timestamp

case class opcusdata(deviceid: String, tagName: String, tagdata: String, tagTime: Timestamp, tagvalue: Double)
case class opcusdata1(deviceid: String, tagName: String, tagdata: String)

// COMMAND ----------

data = spark.createDataFrame([(deviceid, tagName, tagdata, tagTime, tagvalue)], ["a", "b"])

data.write.jdbc(connectionString, "<TableName>", mode="append")

// COMMAND ----------

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
                                                  df1.write.mode(SaveMode.Append).jdbc(jdbcUrl, "opcuadata", connectionProperties)
                                                  println(deviceid + ": " + tagName + ": " + tagdata + ":" + tagTime + ": " + tagValue )
                                                }
                                                
                                              }
                                              
                                              columncount + 1
                                            }                                   
                                           )
                                            
                          println("row completed")
                        })