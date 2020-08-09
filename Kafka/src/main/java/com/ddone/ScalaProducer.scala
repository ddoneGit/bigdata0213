package com.ddone

import java.util.Properties

import org.apache.kafka.clients.producer._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/8/2-17:11
 *
 */
object ScalaProducer {
  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092")
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](properties)
    Range(1,10).foreach(
      count=>{
        producer.send(new ProducerRecord[String,String]("first",count.toString))
      }
    )
    producer.close()
  }
}
