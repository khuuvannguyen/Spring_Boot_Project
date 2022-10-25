package com.vannguyen.SpringBootProject.domain.entities;

import com.vannguyen.SpringBootProject.application.responses.OrderDetailResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_orderDetail")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Order order;

    private Long price;

    private int quantity;

    private Long total;

    public OrderDetail(Product product, Order order, Long price, int quantity) {
        this.product = product;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
        this.total = price * quantity;
    }

    public OrderDetail(UUID id, Product product, Order order, Long price, int quantity) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
        this.total = price * quantity;
    }

    public OrderDetailResponse toResponse() {
        return new OrderDetailResponse(id, product.toResponse(), price, quantity, total);
    }

    @PreRemove
    public void reRemove() {
        this.order.getOrderDetails().clear();
        this.order = null;
    }
}
