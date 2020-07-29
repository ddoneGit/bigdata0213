package com.ddone.source

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-14:51
 *
 */
object Flink_Source_Socket {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    socketDS.print()
    env.execute("Flink_Source_Socket")
  }

}
