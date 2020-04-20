package com.example.producerKafkaRk

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(private var kafkaTemplate: KafkaTemplate<String, Student>) {

    var TOPIC: String = "demoKafka"

    @GetMapping("/producer/{name}")
    fun post(@PathVariable("name")name: String): String {
        var n = Student(name,"IT")
        kafkaTemplate.send(TOPIC, n)
        return "Publish successfulllllll !"
    }
}