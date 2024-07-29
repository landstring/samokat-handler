package com.example.samokathandler.repositories.user;

import com.example.samokathandler.entities.user.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {

}
