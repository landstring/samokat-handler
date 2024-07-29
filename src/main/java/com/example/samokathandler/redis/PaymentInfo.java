package com.example.samokathandler.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("PaymentInfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo implements Serializable {
    @Id
    private String paymentCode;
    private String orderId;
}
