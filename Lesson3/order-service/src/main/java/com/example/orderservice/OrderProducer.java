package com.example.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendOrderCreatedEvent(String orderId){
        System.out.println("Bat dau phat ra event "+orderId);
        kafkaTemplate.send("order-created",orderId)
                .thenAccept(result->{
                    System.out.println("Order gui event thanh cong partition :"+result.getRecordMetadata().partition()
                    +"voi Offset "+result.getRecordMetadata().offset()
                    );
                }).exceptionally(ex->{
                    System.err.println(" Order gui event that bai"+ex.getMessage());
                    return null;
                });
    }
}
