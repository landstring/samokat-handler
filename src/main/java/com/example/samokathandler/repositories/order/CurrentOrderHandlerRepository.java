package com.example.samokathandler.repositories.order;

import com.example.samokathandler.entities.currentOrder.CurrentOrderHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CurrentOrderHandlerRepository {
    private final static String HASH_KEY = "CurrentOrderHandler";
    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<CurrentOrderHandler> findById(String id) {
        return getCurrentOrderClient(id);
    }

    public void save(CurrentOrderHandler currentOrderClient) {
        putCurrentOrderClient(currentOrderClient);
    }

    public void delete(String id) {
        deleteCurrentOrderClient(id);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private Optional<CurrentOrderHandler> getCurrentOrderClient(String key) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(HASH_KEY, key)).map(object -> (CurrentOrderHandler) object);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private void putCurrentOrderClient(CurrentOrderHandler currentOrderClient) {
        redisTemplate.opsForHash().put(HASH_KEY, currentOrderClient.getId(), currentOrderClient);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private void deleteCurrentOrderClient(String key) {
        redisTemplate.opsForHash().delete(HASH_KEY, key);
    }

}
