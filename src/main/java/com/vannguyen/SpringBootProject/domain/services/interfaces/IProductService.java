package com.vannguyen.SpringBootProject.domain.services.interfaces;

import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.application.responses.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    List<ProductResponse> get();

    ProductResponse get(UUID id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(UUID id, ProductRequest request);

    void delete(UUID id);
}
