package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.requests.OrderDetailRequest;
import com.vannguyen.SpringBootProject.application.requests.OrderRequest;
import com.vannguyen.SpringBootProject.application.responses.OrderResponse;
import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.entities.Order;
import com.vannguyen.SpringBootProject.domain.entities.OrderDetail;
import com.vannguyen.SpringBootProject.domain.entities.Product;
import com.vannguyen.SpringBootProject.domain.repositories.OrderDetailRepository;
import com.vannguyen.SpringBootProject.domain.repositories.OrderRepository;
import com.vannguyen.SpringBootProject.domain.repositories.ProductRepository;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository _repoOrder;

    @Autowired
    OrderDetailRepository _repoOrderDetail;

    @Autowired
    ProductRepository _repoProduct;

    @Override
    public List<OrderResponse> get() {
        List<Order> list = _repoOrder.findAll();
        if (list.isEmpty())
            return new ArrayList<>();
        List<OrderResponse> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toResponse()));
        return result;
    }

    @Override
    public OrderResponse get(UUID id) {
        return null;
    }

    @Transactional
    @Override
    public OrderResponse create(OrderRequest request) {
        Order orderEntity = new Order();
        List<UUID> productListId = new ArrayList<>();
        request.getOrderDetails().forEach(i -> productListId.add(i.getProduct()));
        List<Product> products = _repoProduct.findByIdIn(productListId);

        HashMap<String, Long> quantityMap = calculatePrice(products, request.getOrderDetails());
        orderEntity.setTotalPrice(quantityMap.get("total"));
        List<OrderDetail> orderDetailEntityList = new ArrayList<>();
        for (Product product : products) {
            orderDetailEntityList.add(
                    new OrderDetail(
                            product,
                            orderEntity,
                            product.getPrice(),
                            Math.toIntExact(quantityMap.get(product.getId().toString()))
                    )
            );
        }

        orderEntity.setOrderDetails(Set.copyOf(orderDetailEntityList));
        Order save = _repoOrder.save(orderEntity);
        return save.toResponse();
    }

    @Transactional
    @Override
    public OrderResponse update(UUID orderId, OrderRequest request) {
        Order orderEntity = _repoOrder.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found for id: " + orderId)
                );

        this.deleteDetailByOrder(orderEntity);

        List<UUID> productListId = new ArrayList<>();
        request.getOrderDetails().forEach(i -> productListId.add(i.getProduct()));
        List<Product> products = _repoProduct.findByIdIn(productListId);

        HashMap<String, Long> quantityMap = calculatePrice(products, request.getOrderDetails());
        orderEntity.setTotalPrice(quantityMap.get("total"));
        List<OrderDetail> orderDetailEntityList = new ArrayList<>();
        for (Product product : products) {
            orderDetailEntityList.add(
                    new OrderDetail(
                            product,
                            orderEntity,
                            product.getPrice(),
                            Math.toIntExact(quantityMap.get(product.getId().toString()))
                    )
            );
        }

        orderEntity.setOrderDetails(Set.copyOf(orderDetailEntityList));
        Order save = null;
        try {
            Order order1 = _repoOrder.findById(orderId).get();
            List<OrderDetail> byOrderId3 = _repoOrderDetail.findByOrderId(orderId);
//            save = _repoOrder.save(orderEntity);    // java.lang.UnsupportedOperationException
//            _repoOrderDetail.saveAll(orderDetailEntityList);    // java.lang.UnsupportedOperationException
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public OrderResponse update(UUID orderId, UUID detailId, OrderRequest request) {
        return null;
    }

    @Transactional
    @Override
    public void delete(UUID id) {

    }

    private HashMap<String, Long> calculatePrice(List<Product> products, List<OrderDetailRequest> productsRequest) {
        long totalPrice = 0L;
        HashMap<String, Long> quantityMap = new HashMap<>();
        for (Product product : products) {
            int quantity = productsRequest.stream()
                    .filter(detail ->
                            detail.getProduct()
                                    .equals(product.getId())
                    ).findFirst().get().getQuantity();
            quantityMap.put(product.getId().toString(), (long) quantity);
            totalPrice += (product.getPrice() * quantity);
        }
        quantityMap.put("total", totalPrice);
        return quantityMap;
    }

    @Transactional
    private void deleteDetailByOrder(Order order){
        List<UUID> collect = order.getOrderDetails()
                .stream().map(OrderDetail::getId)
                .collect(Collectors.toList());
        _repoOrderDetail.deleteAllById(collect);
    }
}
