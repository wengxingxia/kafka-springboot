package com.xander.kafka.consumer;

import com.xander.kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Description: Kafka消费者: 消息转发
 * 在遇到可重试错误时，把错误写入一个独立的主题, 一个独立的消费者群组负责从该主题上读取错误消息，并进行重试，这种模式有点像其他消息系统里的 `dead-letter-queue`
 *
 * @author Xander
 * datetime: 2021-01-10 10:32
 */
// @Component
public class KafkaConsumerSendTo {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消息转发
     *
     * @param record
     */
    @KafkaListener(topics = {KafkaConfig.TOPIC_SPRING_KAFKA}, groupId = "sendToGroupId")
    @SendTo("test")
    public String onListen(ConsumerRecord<String, String> record) {
        this.logger.info("转发消息到test主题 ----- 主题：" + record.topic() + "-分区：" + record.partition() + "-key：" + record.key()
                + "-value：" + record.value() + "-偏移量：" + record.offset());
        // return的数据就是转发到 test 主题的消息
        return record.value();
    }


}
