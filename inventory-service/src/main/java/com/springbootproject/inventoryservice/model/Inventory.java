package com.springbootproject.inventoryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="inventory-tbl")
public class Inventory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String skuCode;
    private Integer quantity;
}
