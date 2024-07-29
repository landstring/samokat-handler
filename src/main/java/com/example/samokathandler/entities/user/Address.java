package com.example.samokathandler.entities.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Address {
    @Id
    private String id;

    private String city;
    private String home;
    private String apartment;
    private String entrance;
    private Integer plate;
    @Indexed
    private String userId;
}
