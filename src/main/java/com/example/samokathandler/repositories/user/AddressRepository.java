package com.example.samokathandler.repositories.user;

import com.example.samokathandler.entities.user.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findByUserId(String user_id);
}
