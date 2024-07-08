package com.example.samokathandler.kafka;

import com.example.samokathandler.DTO.order.NewOrderDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "newOrder",
            groupId = "newOrderGroupId",
            containerFactory = "factory"
    )
    void listeners(ConsumerRecord<String, NewOrderDto> message){
        System.out.println(message.value());
    }
}
