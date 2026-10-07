package com.example.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String orderId;

    private Long medicineId;

    private Integer quantity;

    private LocalDateTime createdAt;
}
