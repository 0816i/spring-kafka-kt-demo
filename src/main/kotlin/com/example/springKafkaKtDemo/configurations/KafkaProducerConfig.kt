package com.example.springKafkaKtDemo.configurations

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaProducerConfig {
    @Primary
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        val config = hashMapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9094",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        )
        val factory = DefaultKafkaProducerFactory<String, String>(config as Map<String, Any>)
        return KafkaTemplate<String, String>(factory)
    }
}
