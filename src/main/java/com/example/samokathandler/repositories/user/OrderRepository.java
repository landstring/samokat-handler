package com.example.samokathandler.repositories.user;

import com.example.samokathandler.entities.user.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
