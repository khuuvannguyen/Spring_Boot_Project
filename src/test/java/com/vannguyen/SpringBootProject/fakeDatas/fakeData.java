package com.vannguyen.SpringBootProject.fakeDatas;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.entities.Category;
import com.vannguyen.SpringBootProject.domain.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class fakeData {
    public static Account getAccount(String username) {
        return new Account(UUID.randomUUID(), username, "password", "roles");
    }

    public static Account getAccountWithoutId(String username) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword("password");
        account.setRoles("roles");
        return account;
    }

    public static AccountRequest getAccountRequest(String username) {
        return new AccountRequest(username, "password", "roles");
    }

    public static List<Account> getAccountList() {
        List<Account> list = new ArrayList<>();
        list.add(getAccount("admin"));
        list.add(getAccount("user"));
        return list;
    }

    public static Category getCategory(String categoryName) {
        return new Category(UUID.randomUUID(), categoryName);
    }

    public static Category getCategoryWithoutId(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        return category;
    }

    public static CategoryRequest getCategoryRequest(String categoryName) {
        return new CategoryRequest(categoryName);
    }

    public static List<Category> getCategoryList() {
        List<Category> result = new ArrayList<>();
        result.add(getCategory("Category 1"));
        result.add(getCategory("Category 2"));
        return result;
    }

    public static Product getProduct(String productName, Category category, Account createdBy, Account updatedBy) {
        return new Product(UUID.randomUUID(), productName, category, createdBy, updatedBy);
    }

    public static Product getProductWithoutId(String productName, Category category, Account createdBy, Account updatedBy) {
        Product product = new Product();
        product.setName(productName);
        product.setCategory(category);
        product.setCreatedBy(createdBy);
        product.setUpdatedBy(updatedBy);
        return product;
    }

    public static ProductRequest getProductRequest(String productName) {
        ProductRequest request = new ProductRequest();
        request.setName(productName);
        request.setCategory(UUID.randomUUID());
        request.setCreatedBy(UUID.randomUUID());
        request.setUpdatedBy(UUID.randomUUID());
        return request;
    }

    public static List<Product> getProductList(Category category, Account createdBy, Account updatedBy) {
        List<Product> result = new ArrayList<>();
        result.add(getProduct("Product 1", category, createdBy, updatedBy));
        result.add(getProduct("Product 2", category, createdBy, updatedBy));
        return result;
    }
}
