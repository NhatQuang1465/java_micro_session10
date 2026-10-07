package com.example.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderProducer orderProducer;
    @PostMapping
    public ResponseEntity<?> createOrder(){
        String orderId = UUID.randomUUID().toString().substring(0,8);
        // luu tru database
        System.out.println("Goi Order Service luu don hang "+orderId);

        // phat ra su kien
        orderProducer.sendOrderCreatedEvent(orderId);

        return ResponseEntity.ok("Don hang da duocj tao thanh cong va dang xu lysgui mail");
    }
}
