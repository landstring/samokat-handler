package com.example.samokathandler.services;

import com.example.samokathandler.DTO.payment.PaymentStatusDto;
import com.example.samokathandler.exceptions.payment.WrongPaymentStatusException;
import com.example.samokathandler.redis.PaymentInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentService {
    private final static String HASH_KEY = "CurrentOrderHandler";
    private final RedisTemplate redisTemplate;

    public void createPayment(String payment_code, String order_id){
        putPaymentInfo(new PaymentInfo(payment_code, order_id));
    }

    public void updatePaymentStatus(PaymentStatusDto paymentStatusDto){
        if (Objects.equals(paymentStatusDto.getStatus(), "Success")){
            System.out.println("Успешная оплата");
            // TODO: 26.07.2024 Логика обновления статуса заказа
        }
        else if (Objects.equals(paymentStatusDto.getStatus(), "Failure")){
            System.out.println("Неуспешная оплата");
            // TODO: 26.07.2024 Логика отмены заказа
        }
        else{
            throw new WrongPaymentStatusException();
        }
        deletePaymentInfo(paymentStatusDto.getPayment_code());
    }

    private PaymentInfo getPaymentInfo(String payment_code){
        return (PaymentInfo) redisTemplate.opsForHash().get(HASH_KEY, payment_code);
    }

    private void deletePaymentInfo(String payment_code){
        redisTemplate.opsForHash().delete(HASH_KEY, payment_code);
    }

    private void putPaymentInfo(PaymentInfo paymentInfo){
        redisTemplate.opsForHash().put(HASH_KEY, paymentInfo.getPayment_code(), paymentInfo);
    }
}
