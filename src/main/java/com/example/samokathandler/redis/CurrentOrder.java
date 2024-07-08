package com.example.samokathandler.redis;

import com.example.samokathandler.DTO.cart.OrderCartItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@RedisHash("CurrentOrder")
@Getter
@Setter
public class CurrentOrder implements Serializable {
    @Id
    private String id;
    private List<OrderCartItem> orderCartItemList;
    private String address_id;
    private String payment_id;
    private LocalDateTime orderDateTime;
    private String status;
}
