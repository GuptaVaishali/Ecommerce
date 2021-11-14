package com.ttn.project.ecommerce.entities.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ttn.project.ecommerce.auditing.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Category extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @NotNull
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PARENT_CATEGORY_ID")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategories;

    @OneToMany(mappedBy = "category")
    private List<CategoryMetadataFieldRelation> metadataFieldValues;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonBackReference(value = "category-subcategory")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @JsonManagedReference(value = "category-subcategory")
    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<CategoryMetadataFieldRelation> getMetadataFieldValues() {
        return metadataFieldValues;
    }

    public void setMetadataFieldValues(List<CategoryMetadataFieldRelation> metadataFieldValues) {
        this.metadataFieldValues = metadataFieldValues;
    }

    @JsonManagedReference(value = "product-category")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
