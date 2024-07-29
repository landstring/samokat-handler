package com.example.samokathandler.services;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.mappers.OrderMapper;
import com.example.samokathandler.repositories.user.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order getOrderById(String orderId){
        return orderRepository.findById(orderId).get();
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public void saveNewOrder(NewOrderDto newOrderDto){
        orderRepository.save(orderMapper.fromNewOrderDto(newOrderDto));
    }
}
