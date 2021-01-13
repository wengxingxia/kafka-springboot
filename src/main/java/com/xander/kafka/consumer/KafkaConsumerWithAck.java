package com.xander.kafka.consumer;

import com.xander.kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Description: Kafka消费者，手动提交偏移量，
 * 需要配置 enable.auto.commit = false 取消自动提交，并且 spring.kafka.listener.ack-mode = manual 消费者消息确认模式改为手动确认
 *
 * 提示：手动提交偏移量，能够最大程度减少重复消费消息，但是在消息未处理完成，提前提交偏移量，也可能导致消息丢失
 * 关于提交偏移量，请参考下面文章的第6节
 * [CSDN同步：kafka-05-消费者] https://blog.csdn.net/qq_20633779/article/details/112335534
 *
 * @author Xander
 * datetime: 2021-01-10 10:32
 */
@Component
public class KafkaConsumerWithAck {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 每次消费单个记录，并且手动提交偏移量
     *
     *
     * @param record
     */
    @KafkaListener(topics = {KafkaConfig.TOPIC_SPRING_KAFKA})
    public void onListenWithAck(ConsumerRecord<?, ?> record, Acknowledgment ack) throws InterruptedException {
        this.logger.info("消费单个记录----- 主题：" + record.topic() + "-分区：" + record.partition() + "-key：" + record.key()
                + "-value：" + record.value() + "-偏移量：" + record.offset());
        //模拟业务逻辑处理。。。
        this.logger.info("业务处理中...");
        TimeUnit.SECONDS.sleep(10);
        // 手动提交偏移量，表示这个偏移量之前的所有记录已经被处理
        ack.acknowledge();
    }
}
