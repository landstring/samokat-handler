package com.example.samokathandler.mappers;

import com.example.samokathandler.DTO.order.PaymentDto;
import com.example.samokathandler.entities.user.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment fromDto(PaymentDto paymentDto) {
        return Payment.builder()
                .card_number(paymentDto.getCard_number())
                .expiration_date(paymentDto.getExpiration_date())
                .cvc(paymentDto.getCvc())
                .build();
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .card_number(payment.getCard_number())
                .expiration_date(payment.getExpiration_date())
                .cvc(payment.getCvc())
                .build();
    }
}
