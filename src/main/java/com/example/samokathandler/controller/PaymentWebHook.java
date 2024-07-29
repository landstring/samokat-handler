package com.example.samokathandler.controller;

import com.example.samokathandler.DTO.payment.PaymentStatusDto;
import com.example.samokathandler.exceptions.payment.WrongPaymentStatusException;
import com.example.samokathandler.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PaymentWebHook {

    private final PaymentService paymentService;

    @GetMapping("/check")
    public ResponseEntity<?> check(
            @RequestHeader("Authorization") String token) {
        if (Objects.equals(token, "PaymentPassword")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(
            @RequestHeader("Authorization") String token,
            @RequestBody PaymentStatusDto paymentStatusDto) {
        if (Objects.equals(token, "PaymentPassword")) {
            try {
                paymentService.updatePaymentStatus(paymentStatusDto);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (WrongPaymentStatusException wrongPaymentStatusException) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
