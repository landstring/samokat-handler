package com.example.samokathandler.services;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.redis.CurrentOrder;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class KafkaService {
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;
    private final CurrentOrderService currentOrderService;

    public void newOrderHandler(NewOrderDto newOrderDto) {
        boolean successSave = currentOrderService.saveNewOrder(newOrderDto);
        if (successSave) {
            sendStatus(newOrderDto.getId(), "ACCEPTED");
        } else {
            sendStatus(newOrderDto.getId(), "CANCELED");
        }

    }

    public void newStatusHandler(NewStatusDto newStatusDto) {
        if (Objects.equals(newStatusDto.getStatus(), "CANCELED")) {
            currentOrderService.cancelOrder(newStatusDto.getOrder_id());
        } else if (Objects.equals(newStatusDto.getStatus(), "PAID")) {
            CurrentOrder currentOrder = currentOrderService.paidOrder(newStatusDto.getOrder_id());
        }
    }

    private void sendStatus(String orderId, String status) {
        NewStatusDto newStatusDto = NewStatusDto.builder()
                .order_id(orderId)
                .status(status)
                .build();
        statusKafkaTemplate.send("newStatus", newStatusDto);
    }

    public void sendToDelivery() {

    }
}
