package com.vannguyen.SpringBootProject.application.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class CategoryResponse {
    private UUID id;
    private String name;
}
