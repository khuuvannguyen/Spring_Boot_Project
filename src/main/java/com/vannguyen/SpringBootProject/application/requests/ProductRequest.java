package com.vannguyen.SpringBootProject.application.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;

    private UUID category;

    private UUID createdBy;

    private UUID updatedBy;
}
