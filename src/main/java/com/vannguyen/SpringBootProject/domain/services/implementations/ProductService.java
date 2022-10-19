package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.controllers.ProductController;
import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.entities.Category;
import com.vannguyen.SpringBootProject.domain.entities.Product;
import com.vannguyen.SpringBootProject.domain.repositories.AccountRepository;
import com.vannguyen.SpringBootProject.domain.repositories.CategoryRepository;
import com.vannguyen.SpringBootProject.domain.repositories.ProductRepository;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IProductService;
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
public class ProductService implements IProductService {
    @Autowired
    ProductRepository _repoProduct;

    @Autowired
    CategoryRepository _repoCategory;

    @Autowired
    AccountRepository _repoAccount;

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public List<ProductResponse> get() {
        List<Product> list = _repoProduct.findAll();
        if (list.isEmpty())
            return new ArrayList<>();
        List<ProductResponse> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toResponse()));
        return result;
    }

    @Override
    public ProductResponse get(UUID id) {
        Optional<Product> result = _repoProduct.findById(id);
        return result.orElseThrow(() ->
                new ResourceNotFoundException("Not found product for id: " + id)
        ).toResponse();
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        validates(request.getCategory(), request.getCreatedBy());
        Product product = new Product();
        Category category = _repoCategory.findById(request.getCategory()).get();
        Account createdBy = _repoAccount.findById(request.getCreatedBy()).get();
        product.setName(request.getName());
        product.setCategory(category);
        product.setCreatedBy(createdBy);
        Product save = _repoProduct.save(product);
        return save.toResponse();
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest request) {
        Optional<Product> productResult = _repoProduct.findById(id);
        if (!productResult.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found product for id: " + id);
        validates(request.getCategory(), request.getUpdatedBy());
        Product product = productResult.get();
        Category category = _repoCategory.findById(request.getCategory()).get();
        Account updatedBy = _repoAccount.findById(request.getUpdatedBy()).get();
        product.setName(request.getName());
        product.setCategory(category);
        product.setUpdatedBy(updatedBy);
        Product save = _repoProduct.save(product);
        return save.toResponse();
    }

    @Override
    public void delete(UUID id) {
        Optional<Product> product = _repoProduct.findById(id);
        if (!product.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found product for id: " + id);
        _repoProduct.deleteById(id);
    }

    private void validates(UUID categoryId, UUID accountId) {
        Optional<Category> categoryResult = _repoCategory.findById(categoryId);
        if (!categoryResult.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        Optional<Account> accountResult = _repoAccount.findById(accountId);
        if (!accountResult.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }

    private void validates(UUID categoryId, UUID createdId, UUID updatedId) {
        validates(categoryId, createdId);
        Optional<Account> accountResult = _repoAccount.findById(createdId);
        if (!accountResult.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }
}
