package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.OrderEvent;
import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.entity.Order;
import com.example.inventoryservice.producer.InventoryOrderProducer;
import com.example.inventoryservice.repository.MedicineRepository;
import com.example.inventoryservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryOrderProducer orderProducer;
    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody OrderEvent request) {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        request.setOrderId(orderId);

        orderProducer.sendOrderEvent(request);

        return ResponseEntity.ok("Don hang da duoc tao thanh cong va dang xu ly");
    }

    @PostMapping("/medicines")
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineRepository.save(medicine));
    }

    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}
