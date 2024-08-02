package com.example.samokathandler.configuration;


import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.NewStatusDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Value("localhost:9092")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfig() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }

    @Bean
    public Map<String, Object> orderConsumerConfig() {
        return propertiesConsumerGenerate(NewOrderDto.class);
    }

    @Bean
    public Map<String, Object> statusConsumerConfig() {
        return propertiesConsumerGenerate(NewStatusDto.class);
    }

    @Bean
    public ProducerFactory<String, NewStatusDto> statusProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, NewStatusDto> statusKafkaTemplate(
            ProducerFactory<String, NewStatusDto> statusProducerFactory
    ) {
        return new KafkaTemplate<>(statusProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, NewOrderDto> orderConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                orderConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>());
    }

    @Bean
    public ConsumerFactory<String, NewStatusDto> statusConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                statusConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>());
    }

    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, NewOrderDto>> orderFactory(
            ConsumerFactory<String, NewOrderDto> orderConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, NewOrderDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConsumerFactory);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, NewStatusDto>> statusFactory(
            ConsumerFactory<String, NewStatusDto> statusConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, NewStatusDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(statusConsumerFactory);
        return factory;
    }

    private Map<String, Object> propertiesConsumerGenerate(Class<?> obj) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, obj);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return properties;
    }
}
