package com.ddone.source

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-14:31
 *
 */
object Flink_Source_EnvTest {
  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val env1: ExecutionEnvironment = ExecutionEnvironment.createLocalEnvironment()
    val env2: ExecutionEnvironment = ExecutionEnvironment.createRemoteEnvironment("hadoop102", 9999, "com.ddone...")

    val env4: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val env5: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment()
    val env6: StreamExecutionEnvironment = StreamExecutionEnvironment.createRemoteEnvironment("hadoop102", 9999, "...")
  }
}
