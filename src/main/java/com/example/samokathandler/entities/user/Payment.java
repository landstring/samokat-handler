package com.example.samokathandler.entities.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Payment {
    @Id
    private String id;

    private String cardNumber;
    private String expirationDate;
    private Integer cvc;
    @Indexed
    private String userId;
}
