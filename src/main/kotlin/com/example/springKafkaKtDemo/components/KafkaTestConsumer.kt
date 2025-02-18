package com.example.springKafkaKtDemo.components

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class KafkaTestConsumer {
    class PartitionBuffer<T> {
        var iteration = 0
        var records = mutableListOf<T>()
    }
    var buffer = HashMap<TopicPartition, PartitionBuffer<ConsumerRecord<String, String>>>()

    @KafkaListener(topics = ["Test"], concurrency = "4")
    fun listner(data: List<ConsumerRecord<String, String>>, consumer: Consumer<String, String>, acknowledgment: Acknowledgment) {
        println(data.size)
        data.forEach {
            val partition = TopicPartition(it.topic(), it.partition())
            buffer.putIfAbsent(partition, PartitionBuffer())
            buffer.get(partition)?.iteration?.plus(1)
            buffer.get(partition)?.records?.add(it)
            val buffer = buffer.get(partition)
            if (isBufferFull(partition) && buffer !== null) {
                val lastRecord = buffer.records.last()
                flush(setOf(partition))
                consumer.commitSync(
                    hashMapOf(
                        TopicPartition(
                            lastRecord.topic(),
                            lastRecord.partition(),
                        ) to OffsetAndMetadata(lastRecord.offset() + 1, null),
                    ),
                    Duration.ofMillis(1000),
                )
                println(consumer.hashCode())
                println(Thread.currentThread().name + lastRecord.topic() + '-' + lastRecord.partition() + " of " + lastRecord.offset() + " is committed")
            }
        }
        println(Thread.currentThread().name + "Loop Done")
    }

    private fun flush(partitions: Set<TopicPartition>) {
        partitions.forEach(buffer::remove)
    }

    private fun isBufferFull(partition: TopicPartition): Boolean {
        val partitionBuffer = buffer.get(partition)

        if (partitionBuffer === null) {
            return false
        }
        return partitionBuffer.records.size >= 10
    }
}
