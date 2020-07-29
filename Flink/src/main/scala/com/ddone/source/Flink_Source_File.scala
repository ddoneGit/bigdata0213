package com.ddone.source

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-14:48
 *
 */
object Flink_Source_File {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val fileDS: DataStream[String] = env.readTextFile("input/word.txt")
    fileDS.print()
    env.execute("Flink_Source_File")
  }
}
