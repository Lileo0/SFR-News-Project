package com.news.application

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class NewsTopicConsumer {
    /*@KafkaListener(topics = ["dev-newsfeed-article"], groupId = "news-group")
    fun listen(ConsumerRecord<String, Object> record) {
        println("Received message: $message")
        // Process the received message here
    }*/

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"],groupId = "\${spring.kafka.consumer.group}")
    fun listen(record: ConsumerRecord<String?, Any>) {
        println("Received Message: " + record.value().toString())
    }

}