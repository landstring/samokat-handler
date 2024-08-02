package com.example.samokathandler.services;

import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.DTO.order.OrderCartItemDto;
import com.example.samokathandler.entities.paymentInfo.PaymentInfo;
import com.example.samokathandler.entities.product.Product;
import com.example.samokathandler.enums.CurrentOrderStatus;
import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;
import com.example.samokathandler.exceptions.product.ProductNotFoundException;
import com.example.samokathandler.exceptions.product.ProductOutOfStockException;
import com.example.samokathandler.mappers.OrderMapper;
import com.example.samokathandler.repositories.order.CurrentOrderHandlerRepository;
import com.example.samokathandler.repositories.product.ProductRepository;
import com.example.samokathandler.repositories.user.OrderRepository;
import com.example.samokathandler.repositories.paymentInfo.PaymentInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewOrderService {
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final CurrentOrderHandlerRepository currentOrderHandlerRepository;
    private final OrderMapper orderMapper;

    public void newOrderHandler(NewOrderDto newOrderDto) {
        boolean successSave = saveNewOrder(newOrderDto);
        NewStatusDto newStatusDto = NewStatusDto.builder()
                .orderId(newOrderDto.getId())
                .build();
        if (successSave) {
            newStatusDto.setStatus(CurrentOrderStatus.ACCEPTED);
        } else {
            newStatusDto.setStatus(CurrentOrderStatus.CANCELED);
        }
        sendStatus(newStatusDto);
    }

    private boolean saveNewOrder(NewOrderDto newOrderDto) {
        try {
            productCountUpdater(newOrderDto.getOrderCartItemDtoList());
            currentOrderHandlerRepository.save(orderMapper.toCurrentOrderFromNewOrderDto(newOrderDto));
            PaymentInfo paymentInfo = PaymentInfo.builder()
                    .paymentCode(newOrderDto.getPaymentCode())
                    .orderId(newOrderDto.getId())
                    .build();
            paymentInfoRepository.save(paymentInfo);
            orderRepository.save(orderMapper.fromNewOrderDto(newOrderDto));
            return true;
        } catch (SamokatHandlerRuntimeException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    private void productCountUpdater(List<OrderCartItemDto> orderCartItemDtoListList) {
        for (OrderCartItemDto orderCartItemDto : orderCartItemDtoListList) {
            Product product = productRepository.findById(orderCartItemDto.getProductId()).orElseThrow(
                    () -> new ProductNotFoundException("Такого продукта не существует")
            );
            if (product.getCount() >= orderCartItemDto.getCount()) {
                product.setCount(product.getCount() - orderCartItemDto.getCount());
                productRepository.save(product);
            } else {
                throw new ProductOutOfStockException("Продукт закончился на складе");
            }
        }
    }

    private void sendStatus(NewStatusDto newStatusDto) {
        statusKafkaTemplate.send("newStatus", newStatusDto);
    }
}
