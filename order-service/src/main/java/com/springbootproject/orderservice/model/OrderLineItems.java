package com.springbootproject.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="order_line_items_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String skuCoode;
    private BigDecimal price;
    private Integer quantity;
}
