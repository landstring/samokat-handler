package com.example.samokathandler.DTO.order;

import com.example.samokathandler.DTO.cart.OrderCartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDto {
    public String id;
    public List<OrderCartItem> orderCartItemList;
    public Long totalPrice;
    public String user_id;
    public String address_id;
    public String payment_id;
    public LocalDateTime orderDateTime;
    public String payment_code;
    public String status;

}
