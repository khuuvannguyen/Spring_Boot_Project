package com.vannguyen.SpringBootProject.domain.repositories;

import com.vannguyen.SpringBootProject.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
