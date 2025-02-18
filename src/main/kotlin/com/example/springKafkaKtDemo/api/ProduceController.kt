package com.example.springKafkaKtDemo.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProduceController {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @PostMapping("/send")
    public fun sendMessage() {
        kafkaTemplate.send("Test", "One", "Test")
        kafkaTemplate.send("Test", "Two", "Test")
        kafkaTemplate.send("Test", "Three", "Test")
        kafkaTemplate.send("Test", "Four", "Test")
        kafkaTemplate.send("Test", "Five", "Test")
        kafkaTemplate.send("Test", "Six", "Test")
        kafkaTemplate.send("Test", "Seven", "Test")
        kafkaTemplate.send("Test", "Eight", "Test")
        kafkaTemplate.send("Test", "Nine", "Test")
        kafkaTemplate.send("Test", "Ten", "Test")
    }
}
