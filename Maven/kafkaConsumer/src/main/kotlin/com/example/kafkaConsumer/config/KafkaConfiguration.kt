package com.example.kafkaConsumer.config

import com.example.kafkaConsumer.model.User
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.HashMap

@Configuration
class KafkaConfiguration {

    @Bean
    public fun consumerFactory(): ConsumerFactory<String, String> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "group_id"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    public fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        var factory: ConcurrentKafkaListenerContainerFactory<String, String> = ConcurrentKafkaListenerContainerFactory()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }


    @Bean
    fun userConsumerFactory(): ConsumerFactory<String, User> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "group_json"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory(config, StringDeserializer(), JsonDeserializer(User::class.java))
    }

    @Bean
    fun userKafkaListenerFactory(): ConcurrentKafkaListenerContainerFactory<String, User>{
        var factory: ConcurrentKafkaListenerContainerFactory<String, User> = ConcurrentKafkaListenerContainerFactory()
        factory.setConsumerFactory(userConsumerFactory())
        return factory
    }

//  The Kafka consumer is NOT thread-safe.
//  All network I/O happens in the thread of the application making the call.
//  It is the responsibility of the user to ensure that multi-threaded access is properly synchronized.
//  Un-synchronized access will result in ConcurrentModificationException.
}


//  It has a concurrency property. For example, container.setConcurrency(3) creates three KafkaMessageListenerContainer instances.
//
//
//If you have six TopicPartition instances are provided and the concurrency is 3;
// each container gets two partitions.
// For five TopicPartition instances, two containers get two partitions, and the third gets one.
// If the concurrency is greater than the number of TopicPartitions,
// the concurrency is adjusted down such that each container gets one partition.