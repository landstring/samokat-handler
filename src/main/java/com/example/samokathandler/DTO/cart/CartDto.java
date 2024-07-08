package com.example.samokathandler.DTO.cart;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    public List<CartItem> cartItemList;
    public Long totalCost;
}
