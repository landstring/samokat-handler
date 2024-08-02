package com.example.samokathandler.entities.currentOrder;

import com.example.samokathandler.entities.user.OrderCartItem;
import com.example.samokathandler.enums.CurrentOrderStatus;
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
public class CurrentOrderHandler implements Serializable {
    @Id
    String id;
    List<OrderCartItem> orderCartItemList;
    String addressId;
    String paymentId;
    LocalDateTime orderDateTime;
    String paymentCode;
    CurrentOrderStatus status;
}
