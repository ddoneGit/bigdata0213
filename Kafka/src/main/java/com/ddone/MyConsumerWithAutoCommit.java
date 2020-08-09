package com.ddone;

/**
 * @author ddone
 * @date 2020/8/2-15:51
 */

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 自动提交offset
 */
public class MyConsumerWithAutoCommit {
    public static void main(String[] args) {
        //1.创建配置文件
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            //设置groupId,enable.auto_commit,auto.commit.interval
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"con");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //2.新建Kafka消费者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(prop);
        //3.订阅主题
        kafkaConsumer.subscribe(Arrays.asList("first"));//需要传一个集合进去,一次可以订阅多个topic
        //4.拉取数据并消费,消费者不用关闭,while(true)
        while (true){
            //拉取的数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(100L));//这个是超时时间,拉取不到会等100s之后,再返回.
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset=%d,key=%s,value=%s,partition=%d\n",record.offset(),record.key(),record.value(),record.partition());
            }
        }
    }
}
