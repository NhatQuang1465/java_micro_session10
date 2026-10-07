package com.example.emailservice;

import com.example.emailservice.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    @KafkaListener(topics = "medicine-stock-events", groupId = "notification-service-group")
    public void handleNotificationEvent(OrderEvent event) {
        log.info("====================================");
        log.info("[Notification Service] Nhan duoc event tu topic medicine-stock-events: OrderId={}, MedicineId={}, Quantity={}",
                event.getOrderId(), event.getMedicineId(), event.getQuantity());

        log.info("Hóa đơn cho đơn hàng {} đã được gửi tới khách hàng", event.getOrderId());
        log.info("====================================");
    }
}

