package com.ddone.process

import com.ddone.bean.SensorReading
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.{KeyedProcessFunction, ProcessFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 **
 *
*@author ddone
 **
 * @date 2020/7/31-20:39
 *
 */
/**
 * Process 实现WordCount
 */
object Flink_Process_WordCount {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //1.设置使用系统时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    //2.flatMap
    //3.map
    val mapDS: DataStream[(String, Int)] = socketDS.process(new MyFlatMapProcessFunction).process(new MyMapProcessFunction)
    //4.分组
    val keyDS: KeyedStream[(String, Int), Tuple] = mapDS.keyBy(0)
    //5.聚合
    val resultDS: DataStream[(String, Int)] = keyDS.process(new MyKeyedProcessFunction)
    //6.打印
    resultDS.print("Process")
    env.execute()
  }

  class MyFlatMapProcessFunction extends ProcessFunction[String,String]{//类似Mapper
    override def processElement(value: String, ctx: ProcessFunction[String, String]#Context, out: Collector[String]): Unit = {
      val splits: Array[String] = value.split(" ")
      splits.foreach(
        word=> out.collect(word)
      )
    }
  }

  class MyMapProcessFunction extends ProcessFunction[String,(String,Int)]{
    override def processElement(value: String, ctx: ProcessFunction[String, (String, Int)]#Context, out: Collector[(String, Int)]): Unit = {
      out.collect((value,1))
    }
  }

 class MyKeyedProcessFunction extends KeyedProcessFunction[Tuple,(String,Int),(String,Int)]{
    lazy val sum: ValueState[Int] = getRuntimeContext.getState(new ValueStateDescriptor[Int]("keyed-sum", classOf[Int]))
    override def processElement(value: (String, Int), ctx: KeyedProcessFunction[Tuple, (String, Int), (String, Int)]#Context, out: Collector[(String, Int)]): Unit = {
      //需要对sum的状态进行保存
      //1.取出sum的值
      val lastSum: Int = sum.value()
      val currentSum: Int = value._2+lastSum
      //3.更新sum的值
      sum.update(currentSum)
      //父类的方法
      out.collect(value._1,sum.value())
    }
  }

}
