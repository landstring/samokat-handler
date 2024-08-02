package com.example.samokathandler.controller;

import com.example.samokathandler.DTO.payment.PaymentStatusDto;
import com.example.samokathandler.exceptions.order.PaymentNotFoundException;
import com.example.samokathandler.exceptions.payment.WrongPaymentPasswordException;
import com.example.samokathandler.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PaymentWebHook {

    private final PaymentService paymentService;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public void check(
            @RequestHeader("Authorization") String sessionToken) {
        paymentService.checkPassword(sessionToken);
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    public void pay(
            @RequestHeader("Authorization") String sessionToken,
            @RequestBody PaymentStatusDto paymentStatusDto) {
        paymentService.checkPassword(sessionToken);
        paymentService.updatePaymentStatus(paymentStatusDto);
    }
}
