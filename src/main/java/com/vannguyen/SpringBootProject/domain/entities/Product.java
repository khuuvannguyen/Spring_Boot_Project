package com.vannguyen.SpringBootProject.domain.entities;

import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "createdBy", updatable = false)
    private Account createdBy;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "updatedBy")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account updatedBy;

    public Product(UUID id, String name, Category category, Account createdBy, Account updatedBy) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public ProductResponse toResponse() {
        if (updatedBy == null)
            return new ProductResponse(id, name, category.toResponse(), createdBy.toResponse(), null);
        return new ProductResponse(id, name, category.toResponse(), createdBy.toResponse(), updatedBy.toResponse());
    }
}
