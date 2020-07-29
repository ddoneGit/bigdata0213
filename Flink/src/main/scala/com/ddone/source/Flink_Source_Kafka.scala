package com.ddone.source

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.kafka.clients.consumer.ConsumerConfig

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-14:53
 *
 */
object Flink_Source_Kafka {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092")
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"ddoneKafka")
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest")
    val kafkaDSteam: DataStream[String] = env.addSource(new FlinkKafkaConsumer011[String]("first", new SimpleStringSchema(), properties))
    kafkaDSteam.print()
    env.execute("Flink_Source_Kafka")
  }
}
