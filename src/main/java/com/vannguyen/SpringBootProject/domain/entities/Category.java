package com.vannguyen.SpringBootProject.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private Set<Product> products;

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public void update(CategoryRequest request){
        name = request.getName();
    }

    public CategoryResponse toResponse(){
        return new CategoryResponse(id, name);
    }
}
