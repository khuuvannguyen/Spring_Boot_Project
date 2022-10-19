package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.controllers.ProductController;
import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.entities.Category;
import com.vannguyen.SpringBootProject.domain.repositories.CategoryRepository;
import com.vannguyen.SpringBootProject.domain.services.interfaces.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository repo;

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public List<CategoryResponse> get() {
        List<Category> all = repo.findAll();
        if (all == null)
            return new ArrayList<>();
        List<CategoryResponse> result = new ArrayList<>();
        all.forEach(i -> result.add(i.toResponse()));
        return result;
    }

    @Override
    public CategoryResponse get(UUID id) {
        Optional<Category> entity = repo.findById(id);
        return entity.orElseThrow(() ->
                new ResourceNotFoundException("Not found category with id: " + id)
        ).toResponse();
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category entity = new Category();
        entity.setName(request.getName());
        Category saved = repo.save(entity);
        return saved.toResponse();
    }

    @Override
    public CategoryResponse update(UUID id, CategoryRequest request) {
        Optional<Category> result = repo.findById(id);
        Category entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Not found category with id: " + id));
        entity.update(request);
        Category saved = repo.save(entity);
        return saved.toResponse();
    }

    @Override
    public void delete(UUID id) {
        Optional<Category> result = repo.findById(id);
        if (!result.isPresent())
            throw new ResourceNotFoundException("Not found category with id: " + id);
        repo.deleteById(id);
    }
}
