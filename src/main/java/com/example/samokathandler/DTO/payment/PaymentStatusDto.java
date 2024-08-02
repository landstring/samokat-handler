package com.example.samokathandler.DTO.payment;

import com.example.samokathandler.enums.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentStatusDto {

    @JsonProperty("payment_code")
    String paymentCode;

    PaymentStatusEnum status;
}
