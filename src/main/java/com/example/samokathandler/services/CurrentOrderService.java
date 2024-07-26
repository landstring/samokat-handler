package com.example.samokathandler.services;

import com.example.samokathandler.DTO.cart.OrderCartItem;
import com.example.samokathandler.DTO.order.NewOrderDto;
import com.example.samokathandler.entities.product.Product;
import com.example.samokathandler.exceptions.product.ProductNotFoundException;
import com.example.samokathandler.mappers.OrderMapper;
import com.example.samokathandler.redis.CurrentOrder;
import com.example.samokathandler.repositories.product.ProductRepository;
import com.example.samokathandler.repositories.user.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CurrentOrderService {
    private final static String HASH_KEY = "CurrentOrderHandler";
    private final RedisTemplate redisTemplate;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    public void saveNewOrder(NewOrderDto newOrderDto){
        List<OrderCartItem> orderCartItemList = newOrderDto.orderCartItemList;
        boolean orderIsCorrect = true;
        for (OrderCartItem orderCartItem : orderCartItemList){
            try {
                Product product = getProductById(orderCartItem.product_id);
                if (product.getCount() < orderCartItem.count){
                    orderIsCorrect = false;
                }
            }
            catch (ProductNotFoundException exception){
                orderIsCorrect = false;
            }
            if (!orderIsCorrect){
                break;
            }
        }
        if (orderIsCorrect){
            for (OrderCartItem orderCartItem : orderCartItemList){
                Product product = getProductById(orderCartItem.product_id);
                product.setCount(product.getCount() - orderCartItem.count);
                saveProduct(product);
            }
            putCurrentOrder(orderMapper.toCurrentOrderFromNewOrderDto(newOrderDto));
            newOrderDto.setStatus("PROCESSING");
            paymentService.createPayment(newOrderDto.payment_code, newOrderDto.id);
            orderRepository.save(orderMapper.fromNewOrderDto(newOrderDto));
        }

    }

    private Product getProductById(Long product_id){
        Optional<Product> optionalProduct = productRepository.findById(product_id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        else{
            throw new ProductNotFoundException();
        }
    }

    private void saveProduct(Product product){
        productRepository.save(product);
    }

    private CurrentOrder getCurrentOrder(String id){
        return (CurrentOrder) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    private void putCurrentOrder(CurrentOrder currentOrder){
        redisTemplate.opsForHash().put(HASH_KEY, currentOrder.getId(), currentOrder);
    }
}
