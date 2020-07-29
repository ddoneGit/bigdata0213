package com.ddone.wc

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/25-14:50
 *
 */
object Flink_WordCount_Bounder {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val lineDS: DataStream[String] = env.readTextFile("input/word.txt")
    val wordDS: DataStream[String] = lineDS.flatMap(_.split(" "))
    val wordToOneDS: DataStream[(String, Int)] = wordDS.map((_, 1))
    val keyDS: KeyedStream[(String, Int), Tuple] = wordToOneDS.keyBy(0)
    val resultDS: DataStream[(String, Int)] = keyDS.sum(1)
    resultDS.print()
    env.execute()
  }
}
