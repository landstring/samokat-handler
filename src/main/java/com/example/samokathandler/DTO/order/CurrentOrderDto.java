package com.example.samokathandler.DTO.order;

import com.example.samokathandler.DTO.cart.OrderCartItem;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class CurrentOrderDto {
    public String id;
    public List<OrderCartItem> orderCartItemList;
    public String address_id;
    public String payment_id;
    public LocalDateTime orderDateTime;
    public String status;

    public CurrentOrderDto(String id,
                           List<OrderCartItem> orderCartItemList,
                           String address_id,
                           String payment_id,
                           LocalDateTime orderDateTime,
                           String status) {
        this.orderCartItemList = orderCartItemList;
        this.address_id = address_id;
        this.payment_id = payment_id;
        this.orderDateTime = orderDateTime;
        this.status = status;
    }
}
