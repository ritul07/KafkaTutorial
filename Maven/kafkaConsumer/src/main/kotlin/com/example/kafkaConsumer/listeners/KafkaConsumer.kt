package com.example.kafkaConsumer.listeners

import com.example.kafkaConsumer.model.User
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {
    @KafkaListener(topics = ["demoKafka"], groupId = "group_id")
    fun consume(message: String): Unit {
        println("Consumed Message : $message")
    }

    @KafkaListener(topics = ["demoKafka_json"], groupId = "group_json", containerFactory = "userKafkaListenerFactory")
    fun consumeJson(user: User): Unit {
        println("Consumed JSON : $user")
    }
}

//  ConcurrentKafkaListenerContainerFactory is used to create containers for annotated methods with @KafkaListener

//  There are two MessageListenerContainer in spring kafka
//
//1- KafkaMessageListenerContainer
//2- ConcurrentMessageListenerContainer
//The KafkaMessageListenerContainer receives all message from all topics or partitions on a single thread.
// The ConcurrentMessageListenerContainer delegates to one or more KafkaMessageListenerContainer instances
//      to provide multi-threaded consumption.