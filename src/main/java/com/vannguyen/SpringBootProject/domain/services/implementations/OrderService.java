package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.requests.OrderDetailRequest;
import com.vannguyen.SpringBootProject.application.requests.OrderRequest;
import com.vannguyen.SpringBootProject.application.responses.OrderResponse;
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
@Transactional
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
        return _repoOrder.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found for id: " + id)
        ).toResponse();
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        Order orderEntity = new Order();
        List<UUID> productListId = request.getOrderDetails()
                .stream().map(OrderDetailRequest::getProduct).collect(Collectors.toList());
        List<Product> products = _repoProduct.findByIdIn(productListId);

        HashMap<String, Long> quantityMap = calculatePrice(products, request.getOrderDetails());
        orderEntity.setTotalPrice(quantityMap.get("total"));
        List<OrderDetail> orderDetailEntityList = products.stream().map(product ->
                new OrderDetail(
                        product,
                        orderEntity,
                        product.getPrice(),
                        Math.toIntExact(quantityMap.get(product.getId().toString()))
                )
        ).collect(Collectors.toList());

        orderEntity.setOrderDetails(Set.copyOf(orderDetailEntityList));
        Order save = _repoOrder.save(orderEntity);
        return save.toResponse();
    }

    @Override
    public OrderResponse update(UUID orderId, OrderRequest request) {
        Order orderEntity = _repoOrder.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found for id: " + orderId)
                );

        this.deleteDetailByOrder(orderEntity);

        List<UUID> productListId = request.getOrderDetails()
                .stream().map(OrderDetailRequest::getProduct).collect(Collectors.toList());
        List<Product> products = _repoProduct.findByIdIn(productListId);

        HashMap<String, Long> quantityMap = calculatePrice(products, request.getOrderDetails());
        orderEntity.setTotalPrice(quantityMap.get("total"));
        List<OrderDetail> orderDetailEntityList = products.stream().map(product ->
                new OrderDetail(
                        product,
                        orderEntity,
                        product.getPrice(),
                        Math.toIntExact(quantityMap.get(product.getId().toString()))
                )
        ).collect(Collectors.toList());

        orderEntity.setOrderDetails(Set.copyOf(orderDetailEntityList));
        Order save = _repoOrder.save(orderEntity);
        return save.toResponse();
    }

    @Override
    public OrderResponse update(UUID orderId, UUID detailId, OrderRequest request) {
        Order order = _repoOrder.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found for id: " + orderId)
                );
        OrderDetail orderDetail = order.getOrderDetails()
                .stream().filter(detail -> detail.getId().equals(detailId))
                .findFirst().orElseThrow(() ->
                        new ResourceNotFoundException("Order Detail not found for id: " + detailId)
                );

        UUID productRequestId = request.getOrderDetails().get(0).getProduct();
        Product newProduct = _repoProduct.findById(productRequestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found for id: " + productRequestId));

        order.getOrderDetails().removeIf(detail -> detail.getProduct().equals(orderDetail.getProduct()));

        orderDetail.setProduct(newProduct);
        int quantityRequest = request.getOrderDetails().get(0).getQuantity();
        orderDetail.setQuantity(quantityRequest);
        orderDetail.setPrice(newProduct.getPrice());
        orderDetail.setTotal(quantityRequest * newProduct.getPrice());
        order.addOrderDetail(orderDetail);

        long totalPrice = order.getOrderDetails().stream().mapToLong(OrderDetail::getTotal).sum();
        order.setTotalPrice(totalPrice);

        Order save = _repoOrder.save(order);
        return save.toResponse();
    }

    @Override
    public void delete(UUID id) {
        Optional<Order> order = _repoOrder.findById(id);
        if (!order.isPresent()) {
            throw new ResourceNotFoundException("Order not found for id: " + id);
        }
        _repoOrderDetail.deleteByOrderId(id);
        _repoOrder.deleteById(id);
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
    private void deleteDetailByOrder(Order order) {
        List<UUID> collect = order.getOrderDetails()
                .stream().map(OrderDetail::getId)
                .collect(Collectors.toList());
        _repoOrderDetail.deleteAllById(collect);
    }
}
