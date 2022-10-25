package com.vannguyen.SpringBootProject.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vannguyen.SpringBootProject.application.responses.OrderDetailResponse;
import com.vannguyen.SpringBootProject.application.responses.OrderResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    private Date datetime = new Date();

    private Long totalPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = null;

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        if (this.orderDetails == null)
            this.orderDetails = orderDetails;
        else {
            this.orderDetails.retainAll(orderDetails);
            this.orderDetails.addAll(orderDetails);
        }
    }

    public void addOrderDetail(OrderDetail orderDetail){
        this.orderDetails.add(orderDetail);
    }

    public OrderResponse toResponse() {
        return new OrderResponse(id, datetime, totalPrice, toDetailResponseSet());
    }

    private Set<OrderDetailResponse> toDetailResponseSet() {
        Set<OrderDetailResponse> resultSet = new HashSet<>();
        for (OrderDetail detail : orderDetails) {
            resultSet.add(detail.toResponse());
        }
        return resultSet;
    }
}
