package com.example.samokathandler.redis;

import com.example.samokathandler.DTO.order.OrderCartItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@RedisHash("CurrentOrderHandler")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrentOrder implements Serializable {
    @Id
    String id;
    List<OrderCartItem> orderCartItemList;
    String address_id;
    String payment_id;
    LocalDateTime orderDateTime;
    String status;
    String paymentCode;
}
