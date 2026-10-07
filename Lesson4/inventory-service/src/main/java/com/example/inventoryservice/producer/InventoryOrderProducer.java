package com.example.inventoryservice.producer;

import com.example.inventoryservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryOrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {
        log.info("Bat dau phat ra event orderId: {}", event.getOrderId());
        kafkaTemplate.send("medicine-stock-events", event.getOrderId(), event)
                .thenAccept(result -> {
                    System.out.println("Order gui event thanh cong partition :" + result.getRecordMetadata().partition()
                            + "voi Offset " + result.getRecordMetadata().offset());
                })
                .exceptionally(ex -> {
                    System.err.println(" Order gui event that bai: " + ex.getMessage());
                    return null;
                });
    }
}
