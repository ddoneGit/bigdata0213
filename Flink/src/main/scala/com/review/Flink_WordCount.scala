package com.review

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/8/2-00:09
 *
 */
object Flink_WordCount {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //2.读取数据流
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    //3.转换数据
    val flatMapDS: DataStream[String] = socketDS.flatMap(_.split(" "))
    val mapDS: DataStream[(String, Int)] = flatMapDS.map((_, 1))
    //4.分组
    val keyDS: KeyedStream[(String, Int), Tuple] = mapDS.keyBy(0)
    //5.求sum
    val resultDS: DataStream[(String, Int)] = keyDS.sum(1)
    resultDS.print("WordCount")
    //6.执行
    env.execute("Flink_WordCount")

  }
}
