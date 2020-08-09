package com.ddone.process

import com.ddone.bean.SensorReading
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * *
 *
 * @author ddone
 *         *
 * @date 2020/7/31-21:26
 *
 */
object Flink_Processor_Sensor {
  def main(args: Array[String]): Unit = {
    //1.创建执行环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    //2.读取数据源
    val lineDStream: DataStream[String] = env.socketTextStream("hadoop102", 9999)

    //3.转换为样例类
    val sensorDStream: DataStream[SensorReading] = lineDStream.map(line => {
      val arr: Array[String] = line.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    //4.分组
    val keyedDStream: KeyedStream[SensorReading, Tuple] = sensorDStream.keyBy("id")

    val resultDS: DataStream[String] = keyedDStream.process(new AlarmKeydProcessFunction)
    sensorDStream.print("Sensors")
    resultDS.print("报警信息")

    //执行
    env.execute("Flink_Processor_Sensor")
  }

  /**
   * 连续十秒温度没有发生变化,报警
   */
  /**
   * 分组后的逻辑
   *获取 温度和闹钟的时间
   * 比较当前温度和上次温度,进行闹钟设定
   * 闹钟输出消息
   *
   */
  class AlarmKeydProcessFunction extends KeyedProcessFunction[Tuple, SensorReading, String] {
    lazy val lastTemp: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("lastTemp", classOf[Double]))
    lazy val lastTS: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("lastTime", classOf[Long]))

    override def processElement(value: SensorReading, ctx: KeyedProcessFunction[Tuple, SensorReading, String]#Context, out: Collector[String]): Unit = {
      //获取当前温度
      val currentTemp: Double= value.temperature
      //获取上次的温度
      val lastT: Double = lastTemp.value()
      //状态更新
      lastTemp.update(currentTemp)
      //比较当前温度和上次温度.
      if (currentTemp >= lastT && lastTS.value() ==0){
        //没有定闹钟
        val curTs: Long = ctx.timerService().currentProcessingTime()
        lastTS.update(curTs+10*1000) //保存闹钟的状态
        //定义10s后的闹钟
        ctx.timerService().registerProcessingTimeTimer(curTs+10*1000)
      }else if (currentTemp<lastT){
        ctx.timerService().deleteProcessingTimeTimer(lastTS.value())//删除闹钟
        lastTS.clear()
      }
    }

    override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Tuple, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
      out.collect("连续10秒温度没有下降")
      lastTS.clear()
    }
  }

}
