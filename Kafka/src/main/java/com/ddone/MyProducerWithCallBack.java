package com.ddone;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * @author ddone
 * @date 2020/8/2-15:23
 */
public class MyProducerWithCallBack {
    //带回调函数
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties pro = new Properties();
        pro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
        pro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        pro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        pro.put(ProducerConfig.ACKS_CONFIG, "-1");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(pro);
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send(new ProducerRecord<String, String>("first", new Scanner(System.in).next()), (x,y)->
                System.out.println("分区号"+x.partition())).get();
        }

        kafkaProducer.close();
    }
}
