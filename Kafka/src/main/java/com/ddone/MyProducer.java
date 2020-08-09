package com.ddone;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

/**
 * @author ddone
 * @date 2020/8/2-14:41
 */
public class MyProducer {
    //最简单的Producer,不带回调函数
    public static void main(String[] args) {
        //1.设置属性(必要的三个,集群地址,K,V序列化)
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        //2.直接new KafkaProducer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);
        //3.producer.send 发送100条数据
        for (int i = 0; i < 100; i++) {
            kafkaProducer.send(new ProducerRecord("first",i%2,Integer.toString(i),Integer.toString(i)));
            //发送的只有数据,啥也没有分区内有序,分区外无序
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        //4.关闭
        kafkaProducer.close();
    }
}
