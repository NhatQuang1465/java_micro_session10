package com.example.emailservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    @KafkaListener(topics = "order-created",groupId = "email-service-group")
    public void consumerOrderCreated(String orderId){
        System.out.println("====================================");
        System.out.println("Email service Nhan duoc event tao don hang voi id"+orderId);

        try {
            System.out.println("Email connect SMTP de gui mail");
            Thread.sleep(10000);
            System.out.println(" Email xong");
        } catch (InterruptedException e) {
            System.err.println("Loi terong luc gui mail ");
        }
        System.out.println("====================================");
    }
}
