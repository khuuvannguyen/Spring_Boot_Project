package com.vannguyen.SpringBootProject.application.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private UUID id;

    private String name;

    private Long price;

    private CategoryResponse category;

    private AccountResponse createdBy;

    private AccountResponse updatedBy;
}
