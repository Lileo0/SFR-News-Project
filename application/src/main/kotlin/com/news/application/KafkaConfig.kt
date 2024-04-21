package com.news.application

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer


@Configuration
@EnableKafka
class KafkaConfig {
    //@Value(value = "\${spring.kafka.bootstrap-servers}")
    //private val bootstrapAddress: String = "localhost:9092"

    /*@Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kStreamsConfig(): KafkaStreamsConfiguration {
        val kafkaSer = KafkaAvroSerializer()
        val kafkaDe = KafkaAvroDeserializer()
        kafkaSer.configure(mapOf("schema.registry.url" to "http://localhost:8090"),false)
        kafkaDe.configure(mapOf("schema.registry.url" to "http://localhost:8090"),false)
        print(bootstrapAddress)
        val props: MutableMap<String, Any> = HashMap()
        props[APPLICATION_ID_CONFIG] = "news-provider-app"
        props[BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.getName()
        props[DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.getName()

        props["schema.registry.url"] = "http://localhost:8090";

        return KafkaStreamsConfiguration(props)
    }*/

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, Any?>? {
        val kafkaDe = KafkaAvroDeserializer()
        kafkaDe.configure(
            mapOf("schema.registry.url" to "http://localhost:8090"),false
        )
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "news-group"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = kafkaDe
        props[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = kafkaDe
        props["schema.registry.url"] = "http://localhost:8090"
        return DefaultKafkaConsumerFactory(props)
    }
}