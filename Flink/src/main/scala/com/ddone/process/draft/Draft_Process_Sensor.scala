package com.ddone.process.draft

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
 * @date 2020/7/31-23:55
 *
 */
object Draft_Process_Sensor {
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

    val resultDS: DataStream[String] = keyedDStream.process(new AlarmProcessFunction)
    sensorDStream.print("Sensors")
    resultDS.print("报警信息")
    //5.执行
    env.execute("Draft_Process_Sensor")
  }

  class AlarmProcessFunction extends KeyedProcessFunction[Tuple, SensorReading, String] {
    lazy private val temperature: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("Temperature", classOf[Double]))
    lazy private val timestamp: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("Timestamp", classOf[Long]))




    override def processElement(value: SensorReading, ctx: KeyedProcessFunction[Tuple, SensorReading, String]#Context, out: Collector[String]): Unit = {
      //1.获取温度及更新
      val curTemp: Double = value.temperature
      val lastTemper: Double = temperature.value()
      temperature.update(curTemp)
      //2.判断
      if (curTemp >= lastTemper && timestamp.value() == 0){
        //设置闹钟
        val curTime: Long = ctx.timerService().currentProcessingTime()
        timestamp.update(curTime+10*1000L)
        ctx.timerService().registerProcessingTimeTimer(curTime+10*1000L)
      }else if(curTemp < lastTemper){
        //删除闹钟
        ctx.timerService().deleteProcessingTimeTimer(timestamp.value())
        timestamp.clear()
      }

      //3.调用闹钟

    }
    override def onTimer(ts: Long, ctx: KeyedProcessFunction[Tuple, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
      out.collect("最近十秒内温度都没有下降")
      timestamp.clear() //闹钟需要清空
    }
  }
  }
