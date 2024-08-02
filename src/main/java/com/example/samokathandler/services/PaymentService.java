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

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${payment-password}")
    private String paymentToken;
    private final PaymentInfoRepository paymentInfoRepository;
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;

    public void checkPassword(String sessionToken) {
        if (!Objects.equals(sessionToken, paymentToken)) {
            throw new WrongPaymentPasswordException("Неверный пароль платёжной системы");
        }
    }

    public void updatePaymentStatus(PaymentStatusDto paymentStatusDto) {
        PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoById(paymentStatusDto.getPaymentCode())
                .orElseThrow(() -> new PaymentNotFoundException("Оплата не найдена"));
        NewStatusDto newStatusDto = NewStatusDto.builder()
                .orderId(paymentInfo.getOrderId())
                .build();
        if (paymentStatusDto.getStatus() == PaymentStatusEnum.SUCCESS) {
            newStatusDto.setStatus(CurrentOrderStatus.PAID);
        } else {
            newStatusDto.setStatus(CurrentOrderStatus.CANCELED);
        }
        statusKafkaTemplate.send("newStatus", newStatusDto);
        paymentInfoRepository.delete(paymentStatusDto.getPaymentCode());
    }
}
