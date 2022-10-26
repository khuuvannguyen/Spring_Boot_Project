package com.vannguyen.SpringBootProject.domain.services;

import com.vannguyen.SpringBootProject.application.responses.OrderResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.entities.Category;
import com.vannguyen.SpringBootProject.domain.entities.Order;
import com.vannguyen.SpringBootProject.domain.entities.OrderDetail;
import com.vannguyen.SpringBootProject.domain.repositories.OrderDetailRepository;
import com.vannguyen.SpringBootProject.domain.repositories.OrderRepository;
import com.vannguyen.SpringBootProject.domain.repositories.ProductRepository;
import com.vannguyen.SpringBootProject.domain.services.implementations.OrderService;
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

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    OrderRepository _repoOrder;

    @Mock
    OrderDetailRepository _repoOrderDetail;

    @Mock
    ProductRepository _repoProduct;

    @InjectMocks
    OrderService _service;

    private final Category category = fakeData.getCategory("Category 1");

    private final Account account = fakeData.getAccount("admin");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void get_returnList() {
        given(_repoOrder.findAll()).willReturn(fakeData.getOrderList());

        List<OrderResponse> responses = _service.get();

        Assertions.assertThat(responses).isNotEmpty().isNotNull();
        Assertions.assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    public void get_returnEmptyList() {
        given(_repoOrder.findAll()).willReturn(new ArrayList<>());

        List<OrderResponse> responses = _service.get();

        Assertions.assertThat(responses).isNullOrEmpty();
    }

    @Test
    public void getById_returnObject() {
        given(_repoOrder.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(fakeData.getOrder()));

        OrderResponse response = _service.get(UUID.randomUUID());

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void getById_throw_notFound() {
        given(_repoOrder.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            _service.get(UUID.randomUUID());
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create_success() {
        List<UUID> ids = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoProduct.findByIdIn(ArgumentMatchers.anyList()))
                .willReturn(fakeData.getProductList(ids, this.category, this.account, this.account));
        given(_repoOrder.save(ArgumentMatchers.any()))
                .willReturn(fakeData.getOrder());

        OrderResponse response = _service.create(fakeData.getOrderRequest(ids));

        verify(_repoOrder, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void create_throw_productNotFound() {
        List<UUID> ids = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoProduct.findByIdIn(ArgumentMatchers.anyList()))
                .willReturn(new ArrayList<>());

        Assertions.assertThatThrownBy(() ->
                _service.create(fakeData.getOrderRequest(ids))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create_throw_productNotFoundById() {
        List<UUID> ids = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoProduct.findByIdIn(ArgumentMatchers.anyList()))
                .willReturn(fakeData.getProductList(ids, this.category, this.account, this.account));

        Assertions.assertThatThrownBy(() ->
                _service.create(fakeData.getOrderRequest(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()))))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_orderId_success() {
        Order order = fakeData.getOrder();
        order.setOrderDetails(fakeData.getOrderDetailList());
        List<UUID> productsIds = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(order));
        doNothing().when(_repoOrderDetail).deleteAllById(any());
        given(_repoProduct.findByIdIn(any()))
                .willReturn(fakeData.getProductList(productsIds, this.category, this.account, null));
        given(_repoOrder.save(any()))
                .willReturn(order);

        OrderResponse response = _service.update(order.getId(), fakeData.getOrderRequest(productsIds));

        verify(_repoOrderDetail, times(1)).deleteAllById(any());
        verify(_repoOrder, times(1)).save(any());
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void update_orderId_throw_orderNotFound() {
        Order order = fakeData.getOrder();
        List<UUID> productsIds = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() ->
                _service.update(order.getId(), fakeData.getOrderRequest(productsIds))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_orderId_throw_productNotFound() {
        Order order = fakeData.getOrder();
        List<UUID> productsIds = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(order));
        doNothing().when(_repoOrderDetail).deleteAllById(any());
        given(_repoProduct.findByIdIn(any()))
                .willReturn(new ArrayList<>());

        Assertions.assertThatThrownBy(() ->
                _service.update(order.getId(), fakeData.getOrderRequest(productsIds))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_orderId_throw_productNotFoundWithIt() {
        Order order = fakeData.getOrder();
        List<UUID> productsIds = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        List<UUID> productsIds_2 = new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(order));
        doNothing().when(_repoOrderDetail).deleteAllById(any());
        given(_repoProduct.findByIdIn(any()))
                .willReturn(fakeData.getProductList(productsIds, this.category, this.account, null));

        Assertions.assertThatThrownBy(() ->
                _service.update(order.getId(), fakeData.getOrderRequest(productsIds_2))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_orderId_detailId_success() {
        Order order = fakeData.getOrder();
        OrderDetail orderDetail = fakeData.getOrderDetail("Product 1");
        order.setOrderDetails(new ArrayList<>(Collections.singleton(orderDetail)));
        List<UUID> productsIds = new ArrayList<>(Collections.singleton(UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(order));
        given(_repoProduct.findById(any()))
                .willReturn(Optional.of(
                        fakeData.getProduct("Product 1", this.category, this.account, null)));
        given(_repoOrder.save(any()))
                .willReturn(order);

        OrderResponse response = _service.update(order.getId(), orderDetail.getId(), fakeData.getOrderRequest(productsIds));

        verify(_repoOrder, times(1)).save(any());
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void update_orderId_detailId_throw_orderNotFound() {
        given(_repoOrder.findById(any()))
                .willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() ->
                _service.update(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        fakeData.getOrderRequest(new ArrayList<>(List.of(UUID.randomUUID())))
                )
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_orderId_detailId_throw_orderDetailNotFound() {
        Order order = fakeData.getOrder();
        OrderDetail orderDetail = fakeData.getOrderDetail("Product 1");
        List<UUID> productsIds = new ArrayList<>(Collections.singleton(UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(order));

        Assertions.assertThatThrownBy(() ->
                _service.update(order.getId(), orderDetail.getId(), fakeData.getOrderRequest(productsIds))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void update_orderId_detailId_productNotFound() {
        Order order = fakeData.getOrder();
        OrderDetail orderDetail = fakeData.getOrderDetail("Product 1");
        order.setOrderDetails(new ArrayList<>(Collections.singleton(orderDetail)));
        List<UUID> productsIds = new ArrayList<>(Collections.singleton(UUID.randomUUID()));
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(order));
        given(_repoProduct.findById(any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() ->
                _service.update(order.getId(), orderDetail.getId(), fakeData.getOrderRequest(productsIds))
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void delete_success() {
        given(_repoOrder.findById(any()))
                .willReturn(Optional.of(fakeData.getOrder()));
        doNothing().when(_repoOrderDetail).deleteByOrderId(any());

        _service.delete(UUID.randomUUID());

        verify(_repoOrderDetail, times(1)).deleteByOrderId(any());
        verify(_repoOrder, times(1)).deleteById(any());
    }

    @Test
    public void delete_throw_orderNotFound() {
        given(_repoOrder.findById(any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() ->
                _service.delete(UUID.randomUUID())
        ).isInstanceOf(ResourceNotFoundException.class);
    }
}