package com.xander.kafka.consumer;

import com.xander.kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Description: Kafka消费者
 *
 * @author Xander
 * datetime: 2021-01-10 10:32
 */
@Component
public class KafkaConsumer {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 每次消费单个记录
     *
     * @param record
     */
    @KafkaListener(topics = {KafkaConfig.TOPIC_SPRING_KAFKA})
    public void onListen(ConsumerRecord<?, ?> record) {
        this.logger.info("消费单个记录----- 主题：" + record.topic() + "-分区：" + record.partition() + "-key：" + record.key()
                + "-value：" + record.value() + "-偏移量：" + record.offset());
    }


}
