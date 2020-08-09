package com.ddone;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author ddone
 * @date 2020/8/2-16:21
 */
//同步提交,commitSync 会阻塞当前线程
public class MyConsumerManualCommit01 {
    public static void main(String[] args) {
        //1.创建消费者
        Properties prop = new Properties();
        //设置参数关闭自动提交
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(prop);
        //2.订阅主题
        kafkaConsumer.subscribe(Arrays.asList("first"));
        //3.拉取数据poll(超时时间),设置同步提交
        while (true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            kafkaConsumer.commitSync();//加上一个方法即可.
        }
        //4.不关闭消费者

    }
}
