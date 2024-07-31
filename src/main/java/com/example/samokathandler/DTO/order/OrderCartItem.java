package com.example.samokathandler.DTO.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCartItem implements Serializable {
    Long productId;
    Integer count;
}
