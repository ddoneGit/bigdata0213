package com.ddone.process.draft

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.functions.{KeyedProcessFunction, ProcessFunction}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/31-21:11
 *
 */
object Draft_Process_WordCount {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象,数据流
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    //2.转换成元祖
    val mapDS: DataStream[(String, Int)] = socketDS.process(new MyDraftProcessFunction)
    //3.分组,聚合
    val keyDS: KeyedStream[(String, Int), Tuple] = mapDS.keyBy(0)
    val result: DataStream[(String, Int)] = keyDS.process(new MyDraftReduceProcessFunction)
    //测试,执行
//    socketDS.print("测试")
    result.print("统计结果:")
    env.execute()
  }

  class MyDraftProcessFunction extends ProcessFunction[String,(String,Int)]{
    override def processElement(value: String, ctx: ProcessFunction[String, (String, Int)]#Context, out: Collector[(String, Int)]): Unit = {
      val words: Array[String] = value.split(" ")
      words.foreach(
        word=>out.collect((word,1))
      )
    }
  }

  class MyDraftReduceProcessFunction extends KeyedProcessFunction[Tuple,(String,Int),(String,Int)]{
    lazy val sum: ValueState[Int] = getRuntimeContext.getState(new ValueStateDescriptor[Int]("Keyed-Sum", classOf[Int]))
    override def processElement(value: (String, Int), ctx: KeyedProcessFunction[Tuple, (String, Int), (String, Int)]#Context, out: Collector[(String, Int)]): Unit = {
      val lastSum: Int = sum.value()
      val currentSum: Int = value._2 + lastSum
      sum.update(currentSum)
      out.collect((value._1,sum.value()))
    }
  }

}
