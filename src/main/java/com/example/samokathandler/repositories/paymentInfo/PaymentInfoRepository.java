package com.example.samokathandler.repositories.paymentInfo;

import com.example.samokathandler.entities.paymentInfo.PaymentInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PaymentInfoRepository {
    private final static String HASH_KEY = "PaymentInfo";
    private final RedisTemplate<String, Object> redisTemplate;

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public Optional<PaymentInfo> findPaymentInfoById(String id) {
        return getPaymentInfo(id);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void save(PaymentInfo paymentInfo) {
        putPaymentInfo(paymentInfo);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void delete(String id) {
        deleteCurrentOrderClient(id);
    }


    private Optional<PaymentInfo> getPaymentInfo(String key) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(HASH_KEY, key)).map(object -> (PaymentInfo) object);
    }


    private void putPaymentInfo(PaymentInfo currentOrderClient) {
        redisTemplate.opsForHash().put(HASH_KEY, currentOrderClient.getPaymentCode(), currentOrderClient);
    }


    private void deleteCurrentOrderClient(String key) {
        redisTemplate.opsForHash().delete(HASH_KEY, key);
    }
}
