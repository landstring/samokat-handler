package com.example.samokathandler.kafka;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.services.CurrentOrderService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaListeners {
    private final CurrentOrderService currentOrderService;
    @KafkaListener(
            topics = "newOrder",
            groupId = "newOrderGroupId",
            containerFactory = "factory"
    )
    void listeners(ConsumerRecord<String, NewOrderDto> message){
        currentOrderService.saveNewOrder(message.value());
    }
}
