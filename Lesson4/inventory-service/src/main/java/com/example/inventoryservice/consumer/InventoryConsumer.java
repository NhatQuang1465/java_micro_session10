package com.example.inventoryservice.consumer;

import com.example.inventoryservice.dto.OrderEvent;
import com.example.inventoryservice.entity.Order;
import com.example.inventoryservice.repository.MedicineRepository;
import com.example.inventoryservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryConsumer {

    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "medicine-stock-events", groupId = "inventory-service-group")
    @Transactional
    public void handleOrderEvent(OrderEvent event) {
        log.info("====================================");
        log.info("[Consumer] Nhan duoc event tu topic medicine-stock-events: OrderId={}, MedicineId={}, Quantity={}", 
                event.getOrderId(), event.getMedicineId(), event.getQuantity());

        // 1. Them moi don hang
        Order order = Order.builder()
                .orderId(event.getOrderId())
                .medicineId(event.getMedicineId())
                .quantity(event.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        log.info("[Consumer] Da them moi don hang voi OrderId: {}", event.getOrderId());

        // 2. Tru stock cua san pham thuoc (UPDATE medicines SET stock = stock - quantity)
        int updatedRows = medicineRepository.decreaseStock(event.getMedicineId(), event.getQuantity());
        if (updatedRows > 0) {
            log.info("[Consumer] Cap nhat tru ton kho thanh cong cho Medicine ID: {}, So luong tru: {}", 
                    event.getMedicineId(), event.getQuantity());
        } else {
            log.warn("[Consumer] Cap nhat ton kho THAT BAI. Medicine ID: {} khong ton tai hoac so luong ton kho khong du!", 
                    event.getMedicineId());
        }
        log.info("====================================");
    }
}
