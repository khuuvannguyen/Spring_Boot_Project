package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import com.vannguyen.SpringBootProject.domain.entities.Category;
import com.vannguyen.SpringBootProject.domain.repositories.CategoryRepository;
import com.vannguyen.SpringBootProject.domain.services.interfaces.ICategoryService;
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

    private List<CategoryResponse> toList(List<Category> list) {
        List<CategoryResponse> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toResponse()));
        return result;
    }

    @Override
    public List<CategoryResponse> get() {
        return toList(repo.findAll());
    }

    @Override
    public CategoryResponse get(UUID id) {
        Optional<Category> entity = repo.findById(id);
        if (entity.isPresent())
            return entity.get().toResponse();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found category with id: " + id);
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
        if (!result.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found category with id: " + id);
        }
        Category entity = result.get();
        entity.update(request);
        Category saved = repo.save(entity);
        return saved.toResponse();
    }

    @Override
    public void delete(UUID id) {
        Optional<Category> result = repo.findById(id);
        if (!result.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found category with id: " + id);
        repo.deleteById(id);
    }
}
