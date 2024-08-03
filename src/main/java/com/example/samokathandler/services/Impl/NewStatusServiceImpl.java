package com.example.samokathandler.services.Impl;

import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.entities.user.OrderCartItem;
import com.example.samokathandler.entities.currentOrder.CurrentOrderHandler;
import com.example.samokathandler.entities.product.Product;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.enums.CurrentOrderStatus;
import com.example.samokathandler.enums.OrderStatus;
import com.example.samokathandler.exceptions.order.CurrentOrderNotFoundException;
import com.example.samokathandler.exceptions.order.OrderNotFoundException;
import com.example.samokathandler.exceptions.product.ProductNotFoundException;
import com.example.samokathandler.repositories.order.CurrentOrderHandlerRepository;
import com.example.samokathandler.repositories.paymentInfo.PaymentInfoRepository;
import com.example.samokathandler.repositories.product.ProductRepository;
import com.example.samokathandler.repositories.user.OrderRepository;
import com.example.samokathandler.services.NewStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewStatusServiceImpl implements NewStatusService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final CurrentOrderHandlerRepository currentOrderHandlerRepository;

    @Override
    public void newStatusHandler(NewStatusDto newStatusDto) {
        if (newStatusDto.getStatus() == CurrentOrderStatus.CANCELED) {
            cancelOrder(newStatusDto.getOrderId());
        } else if (newStatusDto.getStatus() == CurrentOrderStatus.PAID) {
            System.out.println("Отправка заказа на доставку");
        }
    }

    private void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Такого заказа не существует"));
        updateOrderStatus(order);
        productCountUpdater(order.getOrderCartItemList());
        CurrentOrderHandler currentOrderHandler = currentOrderHandlerRepository.findById(orderId)
                .orElseThrow(() -> new CurrentOrderNotFoundException("Такого текущего заказа не существует"));
        cancelPayment(currentOrderHandler.getPaymentCode());
        currentOrderHandlerRepository.delete(orderId);
    }

    private void updateOrderStatus(Order order) {
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private void productCountUpdater(List<OrderCartItem> orderCartItemList) {
        for (OrderCartItem orderCartItem : orderCartItemList) {
            Product product = productRepository.findById(orderCartItem.getProductId()).orElseThrow(
                    () -> new ProductNotFoundException("Такого продукта не существует")
            );
            product.setCount(product.getCount() + orderCartItem.getCount());
            productRepository.save(product);
        }
    }

    private void cancelPayment(String paymentCode) {
        paymentInfoRepository.delete(paymentCode);
    }
}
