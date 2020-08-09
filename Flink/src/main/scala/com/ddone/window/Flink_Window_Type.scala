package com.ddone.window

import com.ddone.bean.SensorReading
import com.ddone.transform.Flink_Transform_KeyBy.MyResuce
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.windows.{GlobalWindow, TimeWindow, Window}
import org.apache.flink.util.Collector

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/29-20:05
 *
 */
object Flink_Window_Type {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //2.获取流数据
    val sourceDS: DataStream[String] = env.socketTextStream("localhost", 9999)
    val sensorDS: DataStream[SensorReading] = sourceDS.map(
      line => {
        val words: Array[String] = line.split(",")
        SensorReading(words(0), words(1).toLong, words(2).toDouble)
      }
    )
    //3.KeyBy
    val keyDS: KeyedStream[SensorReading, Tuple] = sensorDS.keyBy(0)
    //4.开窗

//    val windowTime1: WindowedStream[SensorReading, Tuple, TimeWindow] = keyDS.timeWindow(Time.seconds(10))
//    val windowTime2: WindowedStream[SensorReading, Tuple, TimeWindow] = keyDS.timeWindow(Time.seconds(15),Time.seconds(5))
val windowCount1: WindowedStream[SensorReading, Tuple, GlobalWindow] = keyDS.countWindow(5L)
//val windowCount2: WindowedStream[SensorReading, Tuple, GlobalWindow] = keyDS.countWindow(10L,2)
    //5.全量处理窗口的数据
    val resultDS1: DataStream[Int] = windowCount1.apply(new MyWindowFunction)
    //6.执行
    val resultDs: DataStream[SensorReading] = windowCount1.reduce(new MyResuce)
//    resultDs.print()
    resultDS1.print("输入个数")
    env.execute("Flink_Window_Type")
  }
  class MyWindowFunction extends WindowFunction[SensorReading, Int, Tuple, GlobalWindow]{
    override def apply(key: Tuple, window: GlobalWindow, input: Iterable[SensorReading], out: Collector[Int]): Unit = {
      out.collect(input.size)
    }
  }
}
