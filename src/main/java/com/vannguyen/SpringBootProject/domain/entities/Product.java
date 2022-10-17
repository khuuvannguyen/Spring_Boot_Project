package com.vannguyen.SpringBootProject.domain.entities;

import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
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

    public ProductResponse toResponse() {
        if (updatedBy == null)
            return new ProductResponse(id, name, category.toResponse(), createdBy.toResponse(), null);
        return new ProductResponse(id, name, category.toResponse(), createdBy.toResponse(), updatedBy.toResponse());
    }
}
