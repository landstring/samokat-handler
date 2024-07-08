package com.example.samokathandler.DTO.order;

import com.example.samokathandler.DTO.cart.CartDto;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
public class OrderDto {
    public CartDto cart;
    public AddressDto address;
    public PaymentDto payment;
    public LocalDateTime orderDateTime;
}
