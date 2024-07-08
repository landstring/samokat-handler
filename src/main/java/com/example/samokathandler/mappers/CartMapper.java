package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.cart.CartDto;
import com.example.samokathandler.DTO.cart.CartItem;
import com.example.samokathandler.DTO.cart.OrderCartItem;
import com.example.samokathandler.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CartMapper {
    private final ProductService productService;

    public CartDto listOrderCartItemToDto(List<OrderCartItem> orderCartItemList){
        List<CartItem> cartItemList = new ArrayList<>();
        long totalCost = 0L;
        for (OrderCartItem orderCartItem : orderCartItemList){
            CartItem cartItem = new CartItem(
                    productService.getProductById(orderCartItem.product_id),
                    orderCartItem.count
            );
            totalCost += productService.getProductById(orderCartItem.product_id).price * orderCartItem.count;
            cartItemList.add(cartItem);
        }
        return new CartDto(cartItemList, totalCost);
    }

    public List<OrderCartItem> toListOrderCartItem(CartDto cartDto){
        List<OrderCartItem> orderCartItemList = new ArrayList<>();
        for (CartItem cartItem : cartDto.cartItemList){
            orderCartItemList.add(new OrderCartItem(cartItem.product.id, cartItem.count));
        }
        return orderCartItemList;
    }
}
