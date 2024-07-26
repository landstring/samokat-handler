package com.example.samokathandler.repositories.user;

import com.example.samokathandler.entities.user.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByUserIdAndStatus(String userId, String status);
}
