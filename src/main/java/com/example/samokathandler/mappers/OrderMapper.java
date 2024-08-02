package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.entities.user.OrderCartItem;
import com.example.samokathandler.enums.OrderStatus;
import com.example.samokathandler.entities.currentOrder.CurrentOrderHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper {

    public CurrentOrderHandler toCurrentOrderFromNewOrderDto(NewOrderDto newOrderDto) {
        return CurrentOrderHandler.builder()
                .id(newOrderDto.getId())
                .orderCartItemList(newOrderDto.getOrderCartItemDtoList()
                        .stream()
                        .map(orderCartItemDto -> OrderCartItem.builder()
                                .productId(orderCartItemDto.getProductId())
                                .count(orderCartItemDto.getCount())
                                .build())
                        .toList())
                .addressId(newOrderDto.getAddressId())
                .paymentId(newOrderDto.getPaymentId())
                .orderDateTime(newOrderDto.getOrderDateTime())
                .status(newOrderDto.getStatus())
                .paymentCode(newOrderDto.getPaymentCode())
                .build();
    }

    public Order fromNewOrderDto(NewOrderDto newOrderDto) {
        return Order.builder()
                .id(newOrderDto.getId())
                .orderCartItemList(newOrderDto.getOrderCartItemDtoList()
                        .stream()
                        .map(orderCartItemDto -> OrderCartItem.builder()
                                .productId(orderCartItemDto.getProductId())
                                .count(orderCartItemDto.getCount())
                                .build())
                        .toList())
                .totalPrice(newOrderDto.getTotalPrice())
                .userId(newOrderDto.getUserId())
                .addressId(newOrderDto.getAddressId())
                .paymentId(newOrderDto.getPaymentId())
                .orderDateTime(newOrderDto.getOrderDateTime())
                .status(OrderStatus.PROCESSING)
                .build();
    }

}
