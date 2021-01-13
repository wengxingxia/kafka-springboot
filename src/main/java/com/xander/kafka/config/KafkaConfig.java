package com.xander.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Description: kafka相关配置
 *
 * @author Xander
 * datetime: 2021-01-08 17:40
 */
@Configuration
public class KafkaConfig {
    /**
     * 主题
     */
    public static final String TOPIC_SPRING_KAFKA = "SpringKafka";

    /**
     * 新建或者更新Topic并设置分区数为3，分区副本数为1，
     * 这里设置仅仅时测试使用，主题的分区数和每个分区的副本数，需要根据业务场景和硬件条件去考虑
     * <p>
     * 我们也可以不手动创建topic，因为kafka server.properties 配置文件中 auto.create.topics.enable 默认为 true，
     * 表示如果主题不存在，则自动创建主题，
     * 分区数量由kafka server.properties 配置文件中 num.partitions 指定，默认是 1
     * 所以如果是自动创建主题，则默认的分区数为1，分区副本数为1
     *
     * @return
     */
    @Bean
    public NewTopic newOrUpdateTopic() {
        // 通过TopicBuilder新建或者update Topic，
        // 注意：主题的分区只能新增，不能减少分区
        return TopicBuilder.name(TOPIC_SPRING_KAFKA).replicas(1).partitions(3).build();
    }

}
