package com.ddone.transform

import com.ddone.bean.SensorReading
import org.apache.flink.api.common.functions.{ReduceFunction, RichMapFunction}
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.streaming.api.scala._
/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-21:15
 *
 */
object Flink_Transform_KeyBy {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //2.获取端口数据
    val socketDS: DataStream[String] = env.socketTextStream("localhost", 9999)
    //3.转换为样列类对象
    val mapDS: DataStream[SensorReading] = socketDS.map(new MyMapFunction)
    //4.按照传感器分组
    val keyDS: KeyedStream[SensorReading, String] = mapDS.keyBy(new MykeySelector)
//    val resultDS: DataStream[SensorReading] = keyDS.max(2)
val resultDS: DataStream[SensorReading] = keyDS.reduce(new MyResuce)
    //打印执行
    resultDS.print()
    env.execute("Flink_Transform_KeyBy")
  }

  //创建RichMapFunction的子类
  class MyMapFunction extends RichMapFunction[String,SensorReading]{
    override def map(value: String): SensorReading = {
      val values: Array[String] = value.split(",")
      SensorReading(values(0),values(1).toLong,values(2).toDouble)
    }
  }

  class MykeySelector extends KeySelector[SensorReading,String]{
    override def getKey(value: SensorReading): String = {
      value.id
    }
  }
  class MyResuce extends ReduceFunction[SensorReading]{
    override def reduce(t1: SensorReading, t2: SensorReading): SensorReading = {
      SensorReading(t1.id,t1.timestamp.min(t2.timestamp),t1.temperature.max(t2.temperature))
    }
  }
}
