package com.news.application

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.fasterxml.jackson.databind.ObjectMapper
import com.news.application.api.model.NewsApiDto
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.GenericRecord
import org.springframework.transaction.CannotCreateTransactionException
import java.lang.Thread.sleep


@Component
class NewsProcessor(val service: NewsService) {

    val objectMapper = ObjectMapper()

    @Autowired
    fun buildPipeline(streamsBuilder: StreamsBuilder) {
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
                    try {
                        service.create(news)
                        println(value)
                    }
                    catch (e: CannotCreateTransactionException){
                        //retry mechanism
                        println(e)
                    }
                }
            // Commit kafka acknowledgement, was a bit tricky here because it is hard to access the kafka consumer beneath the stream to acknowledge }
    }
    companion object {
        private val STRING_SERDE = Serdes.String()
    }
}