package com.example.samokathandler.DTO.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentStatusDto {
    String payment_code;
    String status;
}
