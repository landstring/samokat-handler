package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.OrderDto;
import com.example.samokathandler.entities.user.Address;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.entities.user.Payment;
import com.example.samokathandler.exceptions.order.AddressNotFoundException;
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
}
