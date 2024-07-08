package com.example.samokathandler.DTO.order;

import com.example.samokathandler.entities.user.Payment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentDto {
    public String id;
    public String card_number;
    public String expiration_date;
    public Integer cvc;

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.card_number = payment.getCard_number();
        this.expiration_date = payment.getExpiration_date();
        this.cvc = payment.getCvc();
    }
}
