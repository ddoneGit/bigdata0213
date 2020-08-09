package com.ddone.watermark

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
 * @date 2020/7/31-15:20
 *WaterMark
 * 步骤:
 *    1.设置系统使用事件时间
 *      env.set...Time...
 *    2.处理DStream
 *
 */
object WaterMarkTest {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(2)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(500) //500ms自动生成
    //0.设置系统使用事件
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    val sensorDS: DataStream[SensorReading] = socketDS.map(
      line => {
        val words: Array[String] = line.split(",")
        SensorReading(words(0), words(1).toLong, words(2).toDouble)
      }
    )
    //1.获取数据时间并指定偏移,需要在分组之前设置
    val waterMarkSensorDS: DataStream[SensorReading] = sensorDS.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(2)) {
      override def extractTimestamp(element: SensorReading): Long = {
        element.timestamp * 1000L
      }
    })
    val keyedDS: KeyedStream[SensorReading, String] = waterMarkSensorDS.keyBy(_.id)
    //2.开窗
    val windowDS: WindowedStream[SensorReading, String, TimeWindow] = keyedDS.timeWindow(Time.seconds(5))
    //3.计算并打印
    val result: DataStream[(Long, Long, Int)] = windowDS.apply(new MyWindowFunction02)
    waterMarkSensorDS.print("waterMarkSensorDS")
    result.print("result")
//    socketDS.print("SocketDS")
//    sensorDS.print("SensorDS")
    env.execute("WaterMarkTest")
  }
  class MyWindowFunction02 extends WindowFunction[SensorReading,(Long,Long,Int),String,TimeWindow]{
    override def apply(key: String, window: TimeWindow, input: Iterable[SensorReading], out: Collector[(Long,Long,Int)]): Unit = {
      out.collect((window.getStart,window.getEnd,input.size))
    }
  }
}
