package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.OrderRequest;
import com.vannguyen.SpringBootProject.application.responses.OrderResponse;
import com.vannguyen.SpringBootProject.application.validators.OrderValidator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    IOrderService _service;

    static OrderValidator validator = new OrderValidator();

    static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Operation(summary = "Get all Order from database", tags = "Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public List<OrderResponse> get() {
        return _service.get();
    }

    @Operation(summary = "Get Order by Id", tags = "Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public OrderResponse get(String id) {
        validator.validate(id);
        return _service.get(UUID.fromString(id));
    }

    @Operation(summary = "Create a new Order", tags = "Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        validator.validate(request);
        return new ResponseEntity<>(_service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing Order", tags = "Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @PutMapping(value = "/{orderId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public OrderResponse update(String orderId, @RequestBody OrderRequest request) {
        validator.validate(orderId, request);
        return _service.update(UUID.fromString(orderId), request);
    }

    @Operation(summary = "Update an existing Order", tags = "Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @PutMapping(value = "/{orderId}/{detailId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public void update(String orderId, String detailId, @RequestBody OrderRequest request) {
        validator.validate(orderId, detailId, request);
        _service.update(UUID.fromString(orderId), UUID.fromString(detailId), request);
    }

    @Operation(summary = "Delete an existing Order", tags = "Order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @DeleteMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public void delete(String id) {
        validator.validate(id);
        _service.delete(UUID.fromString(id));
    }
}
