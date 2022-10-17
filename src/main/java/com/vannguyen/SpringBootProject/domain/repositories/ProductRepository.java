package com.vannguyen.SpringBootProject.domain.repositories;

import com.vannguyen.SpringBootProject.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
