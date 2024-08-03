package com.example.samokathandler.services.Impl;

import com.example.samokathandler.DTO.order.NewStatusDto;
import com.example.samokathandler.DTO.payment.PaymentStatusDto;
import com.example.samokathandler.entities.paymentInfo.PaymentInfo;
import com.example.samokathandler.enums.CurrentOrderStatus;
import com.example.samokathandler.enums.PaymentStatusEnum;
import com.example.samokathandler.exceptions.order.PaymentNotFoundException;
import com.example.samokathandler.exceptions.payment.WrongPaymentPasswordException;
import com.example.samokathandler.repositories.paymentInfo.PaymentInfoRepository;
import com.example.samokathandler.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Value("${payment-password}")
    private String paymentToken;
    private final PaymentInfoRepository paymentInfoRepository;
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;

    @Override
    public void checkPassword(String sessionToken) {
        if (!Objects.equals(sessionToken, paymentToken)) {
            throw new WrongPaymentPasswordException("Неверный пароль платёжной системы");
        }
    }

    @Override
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
        sendStatusMessage(newStatusDto);
        paymentInfoRepository.delete(paymentStatusDto.getPaymentCode());
    }


    private void sendStatusMessage(NewStatusDto newStatusDto){
        log.info("Отправлено сообщение в Kafka в топик {}. Сообщение: {}", "newStatusHandler", newStatusDto);
        statusKafkaTemplate.send("newStatusHandler", newStatusDto);
    }
}
