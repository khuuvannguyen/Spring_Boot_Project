package com.vannguyen.SpringBootProject.domain.services.interfaces;

import com.vannguyen.SpringBootProject.application.requests.OrderRequest;
import com.vannguyen.SpringBootProject.application.responses.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    List<OrderResponse> get();

    OrderResponse get(UUID id);

    OrderResponse create(OrderRequest request);

    OrderResponse update(UUID orderId, OrderRequest request);

    OrderResponse update(UUID orderId, UUID detailId, OrderRequest request);

    void delete(UUID id);
}
