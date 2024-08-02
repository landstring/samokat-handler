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
    public ResponseEntity<?> check(
            @RequestHeader("Authorization") String sessionToken) {
        try {
            paymentService.checkPassword(sessionToken);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (WrongPaymentPasswordException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(
            @RequestHeader("Authorization") String sessionToken,
            @RequestBody PaymentStatusDto paymentStatusDto) {
        try {
            paymentService.checkPassword(sessionToken);
            paymentService.updatePaymentStatus(paymentStatusDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (WrongPaymentPasswordException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PaymentNotFoundException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
