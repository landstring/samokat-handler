package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.redis.CurrentOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {

    public CurrentOrder toCurrentOrderFromNewOrderDto(NewOrderDto newOrderDto) {
        return CurrentOrder.builder()
                .id(newOrderDto.getId())
                .orderCartItemList(newOrderDto.getOrderCartItemList())
                .address_id(newOrderDto.getAddress_id())
                .payment_id(newOrderDto.getPayment_id())
                .orderDateTime(newOrderDto.getOrderDateTime())
                .status(newOrderDto.getStatus())
                .paymentCode(newOrderDto.getPayment_code())
                .build();
    }

    public Order fromNewOrderDto(NewOrderDto newOrderDto) {
        return Order.builder()
                .id(newOrderDto.getId())
                .orderCartItemList(newOrderDto.getOrderCartItemList())
                .totalPrice(newOrderDto.getTotalPrice())
                .userId(newOrderDto.getUser_id())
                .address_id(newOrderDto.getAddress_id())
                .payment_id(newOrderDto.getPayment_id())
                .orderDateTime(newOrderDto.getOrderDateTime())
                .status(newOrderDto.getStatus())
                .build();
    }

}
