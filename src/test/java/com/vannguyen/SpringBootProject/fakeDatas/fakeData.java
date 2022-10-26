package com.vannguyen.SpringBootProject.fakeDatas;

import com.vannguyen.SpringBootProject.application.requests.*;
import com.vannguyen.SpringBootProject.domain.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return new Product(UUID.randomUUID(), productName, 1000L, category, createdBy, updatedBy);
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

    public static List<Product> getProductList(List<UUID> ids, Category category, Account createdBy, Account updatedBy) {
        List<Product> resultList = new ArrayList<>();
        int i = 1;
        for (UUID id : ids) {
            resultList.add(new Product(id, "Product " + (i++), 1000L, category, createdBy, updatedBy));
        }

        return resultList;
    }

    public static OrderDetail getOrderDetail(String productName) {
        OrderDetail detail = new OrderDetail();
        detail.setId(UUID.randomUUID());
        Product product = getProduct(productName, getCategory("Category 1"),
                getAccount("admin"), null);
        detail.setProduct(product);
        detail.setTotal(1000L);
        detail.setQuantity(2);
        detail.setPrice(500L);
        return detail;
    }

    public static OrderDetail getOrderDetailWithoutId(String productName) {
        OrderDetail detail = new OrderDetail();
        Product product = getProduct(productName, getCategory("Category 1"),
                getAccount("admin"), null);
        detail.setProduct(product);
        detail.setTotal(1000L);
        detail.setQuantity(2);
        detail.setPrice(500L);
        return detail;
    }

    public static List<OrderDetail> getOrderDetailList() {
        List<OrderDetail> list = new ArrayList<>();
        list.add(getOrderDetail("Product 1"));
        list.add(getOrderDetail("Product 2"));
        return list;
    }

    public static OrderDetailRequest getOrderDetailRequest(UUID id) {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setProduct(id);
        request.setQuantity(1);
        return request;
    }

    public static Order getOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setOrderDetails(getOrderDetailList());
        order.setTotalPrice(1000L);
        return order;
    }

    public static Order getOrderWithoutId() {
        Order order = new Order();
        order.setOrderDetails(getOrderDetailList());
        order.setTotalPrice(2000L);
        return order;
    }

    public static List<Order> getOrderList() {
        List<Order> list = new ArrayList<>();
        list.add(getOrder());
        list.add(getOrder());
        return list;
    }

    public static OrderRequest getOrderRequest(List<UUID> ids) {
        OrderRequest request = new OrderRequest();
        List<OrderDetailRequest> detailRequestList = ids.stream()
                .map(fakeData::getOrderDetailRequest).collect(Collectors.toList());
        request.setOrderDetails(detailRequestList);
        return request;
    }
}
