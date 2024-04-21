package com.news.application

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class NewsTopicConsumer {
    @KafkaListener(topics = ["dev-newsfeed-article"], groupId = "news-group")
    fun listen(message: String) {
        println("Received message: $message")
        // Process the received message here
    }
}