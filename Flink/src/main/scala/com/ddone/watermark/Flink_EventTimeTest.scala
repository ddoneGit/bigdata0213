package com.ddone.watermark

import com.ddone.bean.SensorReading
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/31-18:09
 *1.确定可以有输出数据,并且转换为样列类
 *2.设置使用事件时间
 * env.
 * 3.提取事件中的时间戳
 *
 * 在分组前设置
 * //选择period
 *4.分组开窗
 * timeWindows
 *
 * 开窗的流必须调用apply方法
 * apply方法里面必须传windowFunction
 *5.设置延迟
 *  在开窗后面进行设置
 *  allowedLateness(Time.seconds(2)) 延迟关窗,再计算
 *6.设置侧输出流
 * 最后用result去获取
 *  在开窗后面设置
 */
object Flink_EventTimeTest {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //1.设置使用系统时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    val sensorReadingDS: DataStream[SensorReading] = socketDS.map(
      line => {
        val words: Array[String] = line.split(",")
        SensorReading(words(0), words(1).toLong, words(2).toDouble)
      }
    )
    //2.提取时间戳,设置waterMart
    val waterMarkSensorDS: DataStream[SensorReading] = sensorReadingDS.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(2)) {
      override def extractTimestamp(element: SensorReading): Long = {
        element.timestamp * 1000L

      }
    })
    //3.分组开窗
    val keyDS: KeyedStream[SensorReading, Tuple] = waterMarkSensorDS.keyBy("id")
    val windowDS: WindowedStream[SensorReading, Tuple, TimeWindow] = keyDS.timeWindow(Time.seconds(5))
      .allowedLateness(Time.seconds(2))
      .sideOutputLateData(new OutputTag[SensorReading]("lateData"))
    val result: DataStream[(Long, Long, Int)] = windowDS.apply(new EventTimeWindowFunction)
    val sideOutPutDS: DataStream[SensorReading] = result.getSideOutput(new OutputTag[SensorReading]("lateData"))
    sideOutPutDS.print("SideDS : ")
    sensorReadingDS.print("sensorReadingDS")
    result.print("WindowEventTime: ")
    env.execute()
  }
//统计需要窗口的大小和输入数据的条数
  class EventTimeWindowFunction extends WindowFunction[SensorReading, (Long,Long,Int), Tuple, TimeWindow]{
    override def apply(key: Tuple, window: TimeWindow, input: Iterable[SensorReading], out: Collector[(Long, Long, Int)]): Unit = {
      out.collect(window.getStart,window.getEnd,input.size)
    }
  }

}
