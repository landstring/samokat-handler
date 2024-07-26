package com.example.samokathandler.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("PaymentInfo")
@Getter
@Setter
@AllArgsConstructor
public class PaymentInfo {
    @Id
    private String payment_code;
    private String order_id;
}
