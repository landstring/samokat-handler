package com.example.samokathandler.entities.user;

import com.example.samokathandler.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@Builder
public class Order {
    @Id
    private String id;

    private List<OrderCartItem> orderCartItemList;
    private Long totalPrice;
    @Indexed
    private String userId;
    private String addressId;
    private String paymentId;

    private LocalDateTime orderDateTime;
    private OrderStatus status;
}
