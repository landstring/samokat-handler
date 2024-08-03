package com.example.samokathandler.services;

import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.DTO.payment.PaymentStatusDto;
import com.example.samokathandler.entities.paymentInfo.PaymentInfo;
import com.example.samokathandler.enums.CurrentOrderStatus;
import com.example.samokathandler.enums.PaymentStatusEnum;
import com.example.samokathandler.exceptions.order.PaymentNotFoundException;
import com.example.samokathandler.exceptions.payment.WrongPaymentPasswordException;
import com.example.samokathandler.repositories.paymentInfo.PaymentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;


public interface PaymentService {

    void checkPassword(String sessionToken);

    void updatePaymentStatus(PaymentStatusDto paymentStatusDto);

}
