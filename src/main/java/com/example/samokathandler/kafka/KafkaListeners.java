package com.example.samokathandler.kafka;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.services.NewOrderService;
import com.example.samokathandler.services.NewStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final NewOrderService newOrderService;
    private final NewStatusService newStatusService;

    @KafkaListener(
            topics = "newOrder",
            groupId = "newOrderGroupId",
            containerFactory = "orderFactory"
    )
    void newOrderListener(ConsumerRecord<String, NewOrderDto> message) {
        log.info("Пришло сообщение в Kafka о новом заказе: {}", message.value());
        newOrderService.newOrderHandler(message.value());
    }

    @KafkaListener(
            topics = "newStatusClient",
            groupId = "newStatusGroupId",
            containerFactory = "statusFactory"
    )
    void newStatusListener(ConsumerRecord<String, NewStatusDto> message) {
        log.info("Пришло сообщение в Kafka о новом статусе заказа: {}", message.value());
        newStatusService.newStatusHandler(message.value());
    }
}
