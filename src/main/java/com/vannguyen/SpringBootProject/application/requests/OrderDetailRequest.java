package com.vannguyen.SpringBootProject.application.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    private UUID product = null;
    private int quantity = 0;
}
