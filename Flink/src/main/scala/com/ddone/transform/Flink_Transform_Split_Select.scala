package com.ddone.transform

import com.ddone.bean.SensorReading
import org.apache.flink.streaming.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-23:56
 *
 */
object Flink_Transform_Split_Select {
  def main(args: Array[String]): Unit = {
    //创建环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //读取流
    val socketDS: DataStream[String] = env.socketTextStream("localhost", 9999)
    val sensorReading: DataStream[SensorReading] = socketDS.map(
      line => {
        val lines: Array[String] = line.split(",")
        SensorReading(lines(0), lines(1).toLong, lines(2).toDouble)
      }
    )

    val splitDS: SplitStream[SensorReading] = sensorReading.split(
      sensor => {
        if (sensor.temperature > 30) {
          List("High")
        } else {
          List("Low")
        }
      }
    )
    val highDS: DataStream[SensorReading] = splitDS.select("High")
    val lowDS: DataStream[SensorReading] = splitDS.select("Low")
    val allDS: DataStream[SensorReading] = splitDS.select("High", "Low")
    highDS.print("High")
    lowDS.print("Low")
    allDS.print("All")
    env.execute()
  }
}
