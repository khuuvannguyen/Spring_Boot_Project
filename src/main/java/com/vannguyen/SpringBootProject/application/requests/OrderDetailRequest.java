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
public class OrderDetailRequest {
    private UUID product = null;
    private int quantity = 0;
}
