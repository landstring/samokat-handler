package com.example.samokathandler.services;

import com.example.samokathandler.DTO.order.DeliveredOrderDto;
import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.mappers.OrderMapper;
import com.example.samokathandler.redis.CurrentOrder;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class KafkaService {
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;
    private final KafkaTemplate<String, DeliveredOrderDto> deliveredOrderKafkaTemplate;
    private final CurrentOrderService currentOrderService;
    private final OrderMapper orderMapper;

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
            System.out.println("Отправка заказа на доставку");
//            CurrentOrder currentOrder = currentOrderService.paidOrder(newStatusDto.getOrder_id());
//            deliveredOrderKafkaTemplate.send(
//                    "deliveryOrder", orderMapper.currentOrderToDeliveredOrderDto(currentOrder)
//            );
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
