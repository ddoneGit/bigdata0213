package com.ddone.window.draft

import com.ddone.bean.SensorReading
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/31-19:11
 *
 */
object DraftEventTime {
  def main(args: Array[String]): Unit = {
    //1.获取数据,线路通畅,设置并行度为1
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    env.setParallelism(1)
    //2.设置使用事件时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //    sensorDS.print("SensorClass")


    val sensorDS: DataStream[SensorReading] = socketDS.map(
        line => {
        val words: Array[String] = line.split(",")
        SensorReading(words(0), words(1).toInt, words(2).toDouble)
      }
    )
    //3.设置WaterMark
    val waterMarkSensorReadingDS: DataStream[SensorReading] = sensorDS.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(2)) {
      override def extractTimestamp(element: SensorReading): Long = {
        element.timestamp * 1000L
      }
    })
    //4.分组,开窗
    val keyedDS: KeyedStream[SensorReading, Tuple] = waterMarkSensorReadingDS.keyBy("id")
    val windowDS: WindowedStream[SensorReading, Tuple, TimeWindow] = keyedDS.timeWindow(Time.seconds(5))
      .allowedLateness(Time.seconds(2))//设置延迟加载
      .sideOutputLateData(new OutputTag[SensorReading]("lateData"))//设置侧输出流
    //5.处理开窗结果,设置延迟,侧输出流
    val result: DataStream[(Long, Long, Int)] = windowDS.apply(new MyDraftEventTimeWindowFunction)
    val sideDS: DataStream[SensorReading] = result.getSideOutput(new OutputTag[SensorReading]("lateData"))
    result.print("Result: ")
    sideDS.print("侧输出流数据")

    //6.执行
    env.execute()
  }

  class MyDraftEventTimeWindowFunction extends WindowFunction[SensorReading,(Long,Long,Int),Tuple,TimeWindow]{
    override def apply(key: Tuple, window: TimeWindow, input: Iterable[SensorReading], out: Collector[(Long, Long, Int)]): Unit = {
      out.collect(window.getStart,window.getEnd,input.size)
    }
  }

}
