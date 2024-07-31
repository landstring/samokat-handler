package com.example.samokathandler.kafka;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.services.KafkaService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaListeners {
    private final KafkaService kafkaService;

    @KafkaListener(
            topics = "newOrder",
            groupId = "newOrderGroupId",
            containerFactory = "orderFactory"
    )
    void newOrderListener(ConsumerRecord<String, NewOrderDto> message) {
        kafkaService.newOrderHandler(message.value());
    }

    @KafkaListener(
            topics = "newStatus",
            groupId = "newStatusGroupId",
            containerFactory = "statusFactory"
    )
    void newStatusListener(ConsumerRecord<String, NewStatusDto> message) {
        kafkaService.newStatusHandler(message.value());
    }
}
