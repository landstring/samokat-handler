package com.example.samokathandler.repositories.user;

import com.example.samokathandler.entities.user.SamokatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SamokatUser, String> {

}
