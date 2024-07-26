package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.OrderDto;
import com.example.samokathandler.entities.user.Address;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.entities.user.Payment;
import com.example.samokathandler.exceptions.order.AddressNotFoundException;
import com.example.samokathandler.redis.CurrentOrder;
import com.example.samokathandler.repositories.user.AddressRepository;
import com.example.samokathandler.repositories.user.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CartMapper cartMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;

    public OrderDto toDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.cart = cartMapper.listOrderCartItemToDto(order.getOrderCartItemList());
        Optional<Address> optionalAddress = addressRepository.findById(order.getAddress_id());
        if (optionalAddress.isPresent()){
            orderDto.address = addressMapper.toDto(optionalAddress.get());
        }
        else{
            throw new AddressNotFoundException();
        }
        Optional<Payment> optionalPayment = paymentRepository.findById(order.getPayment_id());
        if (optionalPayment.isPresent()){
            orderDto.payment = paymentMapper.toDto(optionalPayment.get());
        }
        else{
            throw new AddressNotFoundException();
        }
        orderDto.orderDateTime = order.getOrderDateTime();
        return orderDto;
    }

    public CurrentOrder toCurrentOrderFromNewOrderDto(NewOrderDto newOrderDto){
        CurrentOrder currentOrder = new CurrentOrder();
        currentOrder.setId(newOrderDto.id);
        currentOrder.setOrderCartItemList(newOrderDto.orderCartItemList);
        currentOrder.setAddress_id(newOrderDto.address_id);
        currentOrder.setPayment_id(newOrderDto.payment_id);
        currentOrder.setOrderDateTime(newOrderDto.orderDateTime);
        currentOrder.setStatus(newOrderDto.getStatus());
        return currentOrder;
    }

    public Order fromNewOrderDto(NewOrderDto newOrderDto){
        Order order = new Order();
        order.setId(newOrderDto.id);
        order.setOrderCartItemList(newOrderDto.orderCartItemList);
        order.setTotalPrice(newOrderDto.totalPrice);
        order.setUserId(newOrderDto.user_id);
        order.setAddress_id(newOrderDto.address_id);
        order.setPayment_id(newOrderDto.payment_id);
        order.setOrderDateTime(newOrderDto.orderDateTime);
        order.setStatus(newOrderDto.status);
        return order;
    }

}
