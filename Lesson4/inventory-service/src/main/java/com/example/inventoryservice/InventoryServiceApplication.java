package com.example.inventoryservice;

import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.repository.MedicineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(MedicineRepository medicineRepository) {
        return args -> {
            if (medicineRepository.count() == 0) {
                medicineRepository.save(Medicine.builder().name("Paracetamol 500mg").stock(100).build());
                medicineRepository.save(Medicine.builder().name("Amoxicillin 500mg").stock(50).build());
                medicineRepository.save(Medicine.builder().name("Ibuprofen 400mg").stock(75).build());
            }
        };
    }
}
