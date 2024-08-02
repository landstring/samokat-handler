package com.example.samokathandler.DTO.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCartItemDto {
    @JsonProperty("product_id")
    Long productId;
    Integer count;
}
