package com.vannguyen.SpringBootProject.domain.services;

import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.entities.Category;
import com.vannguyen.SpringBootProject.domain.entities.Product;
import com.vannguyen.SpringBootProject.domain.repositories.AccountRepository;
import com.vannguyen.SpringBootProject.domain.repositories.CategoryRepository;
import com.vannguyen.SpringBootProject.domain.repositories.ProductRepository;
import com.vannguyen.SpringBootProject.domain.services.implementations.ProductService;
import com.vannguyen.SpringBootProject.fakeDatas.fakeData;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    ProductRepository _repoProduct;

    @Mock
    CategoryRepository _repoCategory;

    @Mock
    AccountRepository _repoAccount;

    @InjectMocks
    ProductService _service;

    private final String PRODUCT_1 = "Product 1";

    private final String PRODUCT_2 = "Product 2";

    private final Category CATEGORY = fakeData.getCategory("Category 1");

    private final Account CREATED_BY = fakeData.getAccount("user1");

    private final Account UPDATED_BY = fakeData.getAccount("user2");

    private final ProductRequest REQUEST = fakeData.getProductRequest(this.PRODUCT_1);

    private final Product PRODUCT = fakeData.getProduct(this.PRODUCT_1, this.CATEGORY, this.CREATED_BY, this.UPDATED_BY);

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void get_returnList() {
        given(_repoProduct.findAll())
                .willReturn(fakeData.getProductList(
                        this.CATEGORY,
                        this.CREATED_BY,
                        this.UPDATED_BY)
                );

        List<ProductResponse> responses = _service.get();

        Assertions.assertThat(responses).isNotNull();
        Assertions.assertThat(responses.get(0).getName()).isEqualTo(this.PRODUCT_1);
        Assertions.assertThat(responses.get(1).getName()).isEqualTo(this.PRODUCT_2);
    }

    @Test
    public void get_returnEmptyList() {
        given(_repoProduct.findAll())
                .willReturn(new ArrayList<>());

        List<ProductResponse> responses = _service.get();

        Assertions.assertThat(responses).isNullOrEmpty();
    }

    @Test
    public void getById_returnObject() {
        findProductById(this.PRODUCT);

        ProductResponse response = _service.get(ArgumentMatchers.any());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(this.PRODUCT_1);
    }

    @Test
    public void getById_throw_notFound() {
        findEmptyProductById();

        Assertions.assertThatThrownBy(() -> {
            UUID id = UUID.randomUUID();
            _service.get(id);
            throw new ResourceNotFoundException("Not found product for id: " + id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create_success() {
        findCategoryById(this.CATEGORY);
        findAccountById(this.CREATED_BY);
        findProductById(this.PRODUCT);
        saveProduct(this.PRODUCT_1);

        ProductResponse response = _service.create(this.REQUEST);

        verify(_repoProduct, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(this.PRODUCT_1);
        Assertions.assertThat(response.getCategory().getName()).isEqualTo(this.CATEGORY.getName());
        Assertions.assertThat(response.getCreatedBy().getUsername()).isEqualTo(this.CREATED_BY.getUsername());
        Assertions.assertThat(response.getUpdatedBy().getUsername()).isEqualTo(this.UPDATED_BY.getUsername());
    }

    @Test
    public void create_throw_category_notFound() {
        findEmptyCategoryById();

        Assertions.assertThatThrownBy(() -> {
            _service.create(this.REQUEST);
            throw new ResourceNotFoundException("Category not found");
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create_throw_account_notFound() {
        findCategoryById(this.CATEGORY);
        findEmptyAccountById();

        Assertions.assertThatThrownBy(() -> {
            _service.create(this.REQUEST);
            throw new ResourceNotFoundException("Account not found");
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_success() {
        findProductById(this.PRODUCT);
        findCategoryById(this.CATEGORY);
        findAccountById(this.UPDATED_BY);
        saveProduct(this.PRODUCT_2);

        ProductResponse response = _service.update(UUID.randomUUID(), fakeData.getProductRequest(this.PRODUCT_2));

        verify(_repoProduct, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isNotEqualTo(this.PRODUCT_1);
        Assertions.assertThat(response.getName()).isEqualTo(this.PRODUCT_2);
    }

    @Test
    public void update_throw_product_notFound() {
        findEmptyProductById();

        Assertions.assertThatThrownBy(() -> {
            UUID id = UUID.randomUUID();
            _service.update(id, fakeData.getProductRequest(this.PRODUCT_2));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found product for id: " + id);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void update_throw_category_notFound(){
        findProductById(this.PRODUCT);
        findEmptyCategoryById();

        Assertions.assertThatThrownBy(() -> {
            UUID id = UUID.randomUUID();
            _service.update(id, fakeData.getProductRequest(this.PRODUCT_2));
            throw new ResourceNotFoundException("Category not found");
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_throw_account_notFound(){
        findProductById(this.PRODUCT);
        findCategoryById(this.CATEGORY);
        findEmptyAccountById();

        Assertions.assertThatThrownBy(() -> {
            UUID id = UUID.randomUUID();
            _service.update(id, fakeData.getProductRequest(this.PRODUCT_2));
            throw new ResourceNotFoundException("Account not found");
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void delete_success(){
        findProductById(this.PRODUCT);

        _service.delete(ArgumentMatchers.any());

        verify(_repoProduct,times(1)).deleteById(ArgumentMatchers.any());
    }

    @Test
    public void delete_throw_product_notFound(){
        findEmptyProductById();

        Assertions.assertThatThrownBy(() -> {
            UUID id = UUID.randomUUID();
            _service.delete(id);
            throw new ResourceNotFoundException("Not found product for id: " + id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    private void saveProduct(String productName) {
        given(_repoProduct.save(ArgumentMatchers.any()))
                .willReturn(fakeData.getProduct(productName, this.CATEGORY, this.CREATED_BY, this.UPDATED_BY));
    }

    private void findCategoryById(Category category) {
        given(_repoCategory.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(category));
    }

    private void findEmptyCategoryById() {
        given(_repoCategory.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());
    }

    private void findAccountById(Account account) {
        given(_repoAccount.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(account));
    }

    private void findEmptyAccountById() {
        given(_repoAccount.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());
    }

    private void findProductById(Product product) {
        given(_repoProduct.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(product));
    }

    private void findEmptyProductById() {
        given(_repoProduct.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());
    }
}