package com.xander.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Description: 自定义Kafka分区器，每条消息都发送到分区0
 *
 * @author Xander
 * datetime: 2021-01-13 19:41
 */
public class XdPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //这里可以根据业务场景将消息路由到不同的分区
        // return 0 表示每条消息都发送到分区0
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
