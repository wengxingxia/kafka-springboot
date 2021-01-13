package com.xander.kafka.producer;

import com.xander.kafka.config.KafkaConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

/**
 * Description: Kafka生产者
 *
 * @author Xander
 * datetime: 2021-01-10 10:29
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 发送消息
    @GetMapping("/{msg}")
    public void send(@PathVariable String msg) throws ExecutionException, InterruptedException {
        long start = Instant.now().toEpochMilli();
        this.logger.info("------start");
        // 发送并忽略结果
        // this.sendAndForget(msg);
        // 同步发送
        this.sendSync(msg);
        // 异步发送
        // this.sendAsync(msg);
        this.logger.info("------end: " + (Instant.now().toEpochMilli() - start));
    }

    /**
     * 发送并忽略结果
     *
     * @param msg
     */
    private void sendAndForget(String msg) {
        kafkaTemplate.send(KafkaConfig.TOPIC_SPRING_KAFKA, msg);
    }

    /**
     * 同步发送
     *
     * @param msg
     */
    private void sendSync(String msg) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConfig.TOPIC_SPRING_KAFKA, msg);
        SendResult<String, String> sendResult = future.get();
        RecordMetadata recordMetadata = sendResult.getRecordMetadata();
        this.logger.info("发送成功：" + recordMetadata.topic() + "--" + recordMetadata.partition() + "---" + recordMetadata.offset());
    }

    /**
     * 异步发送
     *
     * @param msg
     */
    private void sendAsync(String msg) {
        kafkaTemplate.send(KafkaConfig.TOPIC_SPRING_KAFKA, msg).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(SendResult<String, String> sendResult) {
                RecordMetadata recordMetadata = sendResult.getRecordMetadata();
                logger.info("发送成功：" + recordMetadata.topic() + "--" + recordMetadata.partition() + "---" + recordMetadata.offset());
            }
        });
    }
}
