package com.example.samokathandler.services;

import com.example.samokathandler.DTO.cart.OrderCartItem;
import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.entities.product.Product;
import com.example.samokathandler.entities.user.Order;
import com.example.samokathandler.exceptions.product.ProductNotFoundException;
import com.example.samokathandler.mappers.OrderMapper;
import com.example.samokathandler.redis.CurrentOrder;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CurrentOrderService {
    private final static String HASH_KEY = "CurrentOrderHandler";
    private final RedisTemplate<String, Object> redisTemplate;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    public boolean saveNewOrder(NewOrderDto newOrderDto) {
        List<OrderCartItem> orderCartItemList = newOrderDto.getOrderCartItemList();
        boolean orderIsCorrect = checkCorrect(orderCartItemList);
        if (orderIsCorrect) {
            for (OrderCartItem orderCartItem : orderCartItemList) {
                Product product = productService.getProductById(orderCartItem.getProductId());
                product.setCount(product.getCount() - orderCartItem.getCount());
                productService.saveProduct(product);
            }
            putCurrentOrder(orderMapper.toCurrentOrderFromNewOrderDto(newOrderDto));
            newOrderDto.setStatus("PROCESSING");
            paymentService.createPayment(newOrderDto.getPayment_code(), newOrderDto.getId());
            orderService.saveNewOrder(newOrderDto);
            return true;
        } else {
            return false;
        }
    }

    public CurrentOrder paidOrder(String orderId) {
        CurrentOrder currentOrder = getCurrentOrder(orderId);
        deleteCurrentOrder(orderId);
        return currentOrder;
    }

    public void cancelOrder(String orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setStatus("CANCELED");
        orderService.saveOrder(order);
        List<OrderCartItem> orderCartItemList = order.getOrderCartItemList();
        for (OrderCartItem orderCartItem : orderCartItemList) {
            Product product = productService.getProductById(orderCartItem.getProductId());
            product.setCount(product.getCount() + orderCartItem.getCount());
            productService.saveProduct(product);
        }
        CurrentOrder currentOrder = getCurrentOrder(orderId);
        paymentService.cancelPayment(currentOrder.getPaymentCode());
        deleteCurrentOrder(orderId);
    }

    private boolean checkCorrect(List<OrderCartItem> orderCartItemList) {
        boolean orderIsCorrect = true;
        for (OrderCartItem orderCartItem : orderCartItemList) {
            try {
                Product product = productService.getProductById(orderCartItem.getProductId());
                if (product.getCount() < orderCartItem.getCount()) {
                    orderIsCorrect = false;
                }
            } catch (ProductNotFoundException exception) {
                orderIsCorrect = false;
            }
            if (!orderIsCorrect) {
                break;
            }
        }
        return orderIsCorrect;
    }

    private CurrentOrder getCurrentOrder(String orderId) {
        return (CurrentOrder) redisTemplate.opsForHash().get(HASH_KEY, orderId);
    }

    private void putCurrentOrder(CurrentOrder currentOrder) {
        redisTemplate.opsForHash().put(HASH_KEY, currentOrder.getId(), currentOrder);
    }

    private void deleteCurrentOrder(String orderId) {
        redisTemplate.opsForHash().delete(HASH_KEY, orderId);
    }
}
