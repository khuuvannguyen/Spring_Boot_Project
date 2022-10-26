package com.vannguyen.SpringBootProject.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_category")
public class Category implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Product> products;

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
