package com.vannguyen.SpringBootProject.application.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class OrderResponse {
    private UUID id;
    private Date datetime;
    private Long totalPrice;
    private Set<OrderDetailResponse> orderDetails;
}
