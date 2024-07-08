package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.CurrentOrderDto;
import com.example.samokathandler.redis.CurrentOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentOrderMapper {
    public CurrentOrderDto toDto(CurrentOrder currentOrder){
        return new CurrentOrderDto(
                currentOrder.getId(),
                currentOrder.getOrderCartItemList(),
                currentOrder.getAddress_id(),
                currentOrder.getPayment_id(),
                currentOrder.getOrderDateTime(),
                currentOrder.getStatus()
            );
    }

    public CurrentOrder fromDto(CurrentOrderDto currentOrderDto){
        CurrentOrder currentOrder = new CurrentOrder();
        currentOrder.setId(currentOrderDto.id);
        currentOrder.setOrderCartItemList(currentOrderDto.orderCartItemList);
        currentOrder.setAddress_id(currentOrderDto.address_id);
        currentOrder.setPayment_id(currentOrderDto.payment_id);
        currentOrder.setOrderDateTime(currentOrderDto.orderDateTime);
        currentOrder.setStatus(currentOrderDto.status);
        return currentOrder;
    }
}
