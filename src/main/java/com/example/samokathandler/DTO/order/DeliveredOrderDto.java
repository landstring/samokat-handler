package com.example.samokathandler.DTO.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveredOrderDto {
    String id;
    List<OrderCartItem> orderCartItemList;
    String address_id;
}
