package com.vannguyen.SpringBootProject.application.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class ProductRequest {
    private String name = "";

    private Long price = 0L;

    private UUID category = null;

    private UUID createdBy = null;

    private UUID updatedBy = null;
}
