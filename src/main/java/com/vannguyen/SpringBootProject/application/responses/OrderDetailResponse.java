package com.vannguyen.SpringBootProject.application.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private UUID id;
    private ProductResponse product;
    private Long price;
    private int quantity;
    private Long total;
}
