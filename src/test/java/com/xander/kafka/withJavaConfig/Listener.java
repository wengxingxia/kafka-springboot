package com.xander.kafka.withJavaConfig;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Description:
 *
 * @author Xander
 * datetime: 2021-01-09 18:15
 */
public class Listener {

    public final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = "test")
    public void listen1(String foo) {
        this.latch1.countDown();
    }

}
