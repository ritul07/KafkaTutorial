package com.example.consumerKafkaRk

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class Listener {

    @KafkaListener(topics = ["demoKafka_json"], groupId = "group_json", containerFactory = "studentKafkaListenerFactory")
    fun consume(student: Student): Unit {
        println("Consumed JSON : $student")
    }
}