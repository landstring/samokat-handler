package com.example.samokathandler.services;

import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.DTO.payment.PaymentStatusDto;
import com.example.samokathandler.exceptions.payment.WrongPaymentStatusException;
import com.example.samokathandler.redis.PaymentInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentService {
    private final static String HASH_KEY = "CurrentOrderHandler";
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;

    public void createPayment(String payment_code, String order_id) {
        putPaymentInfo(new PaymentInfo(payment_code, order_id));
    }

    public void cancelPayment(String payment_code) {
        deletePaymentInfo(payment_code);
    }


    public void updatePaymentStatus(PaymentStatusDto paymentStatusDto) {
        if (Objects.equals(paymentStatusDto.getStatus(), "Success")) {
            sendStatus(getPaymentInfo(paymentStatusDto.getPayment_code()).getOrderId(), "PAID");
        } else if (Objects.equals(paymentStatusDto.getStatus(), "Failure")) {
            sendStatus(getPaymentInfo(paymentStatusDto.getPayment_code()).getOrderId(), "CANCELED");
        } else {
            throw new WrongPaymentStatusException();
        }
        deletePaymentInfo(paymentStatusDto.getPayment_code());
    }

    private void sendStatus(String orderId, String status) {
        NewStatusDto newStatusDto = NewStatusDto.builder()
                .order_id(orderId)
                .status(status)
                .build();
        statusKafkaTemplate.send("newStatus", newStatusDto);
    }

    private PaymentInfo getPaymentInfo(String payment_code) {
        return (PaymentInfo) redisTemplate.opsForHash().get(HASH_KEY, payment_code);
    }

    private void deletePaymentInfo(String payment_code) {
        redisTemplate.opsForHash().delete(HASH_KEY, payment_code);
    }

    private void putPaymentInfo(PaymentInfo paymentInfo) {
        redisTemplate.opsForHash().put(HASH_KEY, paymentInfo.getPaymentCode(), paymentInfo);
    }
}
