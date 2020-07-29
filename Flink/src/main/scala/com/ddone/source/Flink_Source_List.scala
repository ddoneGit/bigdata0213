package com.ddone.source

import com.ddone.bean.SensorReading
import org.apache.flink.streaming.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-14:36
 *
 */
object Flink_Source_List {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val listDS: DataStream[SensorReading] = env.fromCollection(
      List(
        SensorReading("sensor_1", 1547718199, 35.8),
        SensorReading("sensor_6", 1547718201, 15.4),
        SensorReading("sensor_7", 1547718202, 6.7),
        SensorReading("sensor_10", 1547718205, 38.1)
      )
    )
    listDS.print()
    env.execute("Flink_Source_List")
  }
}
