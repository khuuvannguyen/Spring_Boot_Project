package com.vannguyen.SpringBootProject.domain.services.interfaces;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<CategoryResponse> get();

    CategoryResponse get(UUID id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(UUID id, CategoryRequest request);

    void delete(UUID id);
}
