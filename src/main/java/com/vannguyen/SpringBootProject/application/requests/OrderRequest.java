package com.vannguyen.SpringBootProject.application.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class OrderRequest {
    private List<OrderDetailRequest> orderDetails = new ArrayList<>();
}
