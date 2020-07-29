package com.ddone.sink

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.kafka.clients.producer.ProducerConfig

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/29-14:39
 *
 */
object Flink_Sink_Kafka {
    def main(args: Array[String]): Unit = {
      //1.获取环境对象
      val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
      //2.获取socket的数据
      val socketDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
      //3.输出到Kafka
//      val properties = new Properties()
//      properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092")
//      val myKafkaProducer = new FlinkKafkaProducer011[String]("first", new SimpleStringSchema(), properties)

      val myKafkaProducer1 = new FlinkKafkaProducer011[String]("hadoop102:9092", "first", new SimpleStringSchema())
      socketDS.addSink(myKafkaProducer1)
      //执行
      socketDS.print("Kafka数据流")
      env.execute("Flink_Sink_Kafka")

    }

}
