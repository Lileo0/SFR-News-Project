package com.news.application

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.ValueMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.fasterxml.jackson.databind.ObjectMapper
import com.news.application.api.model.NewsApiDto
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.common.record.Record
import org.apache.kafka.streams.kstream.Produced

@Component
class NewsProcessor(val service: NewsService) {

    val objectMapper = ObjectMapper()

    @Autowired
    fun buildPipeline(streamsBuilder: StreamsBuilder) {
        val objectMapper = ObjectMapper()

        val kafkaDe = KafkaAvroDeserializer()
        kafkaDe.configure(
            mapOf("schema.registry.url" to "http://localhost:8090"),false
        )
        val kafkaSer = KafkaAvroSerializer()
        kafkaSer.configure(
            mapOf("schema.registry.url" to "http://localhost:8090"),false
        )
        val messageStream = streamsBuilder
            .stream("dev-newsfeed-article", Consumed.with(STRING_SERDE,Serdes.serdeFrom(kafkaSer,kafkaDe)))
        messageStream
            .foreach { key, value ->
                val record = value as GenericRecord
                val news = NewsApiDto()
                news.title = record["title"].toString()
                news.text = record["text"].toString()
                news.date = record["date"].toString()
                news.author = record["author"].toString()
                service.create(news)
                println(value)
            }
        messageStream.to("news-output")
    }

    fun processEvent(){

    }
    companion object {
        private val STRING_SERDE = Serdes.String()
    }
}