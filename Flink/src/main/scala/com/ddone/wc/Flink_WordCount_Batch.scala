package com.ddone.wc

import org.apache.flink.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/25-14:46
 *
 */
object Flink_WordCount_Batch {
  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val lineDS: DataSet[String] = env.readTextFile("input/word.txt")
    val wordDS: DataSet[String] = lineDS.flatMap(_.split(" "))
    val wordToOneDS: DataSet[(String, Int)] = wordDS.map((_, 1))
    val groupDS: GroupedDataSet[(String, Int)] = wordToOneDS.groupBy(0)
    val resultDS: AggregateDataSet[(String, Int)] = groupDS.sum(1)
    resultDS.print()
  }
}
