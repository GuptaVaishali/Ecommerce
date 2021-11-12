package com.ttn.project.ecommerce.entities.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ttn.project.ecommerce.entities.registration.Seller;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotEmpty
    private String name;
    private String description;

    @JsonProperty                   // used so that json matches property name ,not getters and setters
    @Column(name = "Is_Cancellable")
    private boolean isCancellable;

    @JsonProperty
    @Column(name = "Is_Returnable")
    private boolean isReturnable;

    @NotNull
    @NotEmpty
    private String brand;

    @JsonProperty
    @Column(name = "Is_Active")
    private boolean isActive;

    @JsonProperty
    @Column(name = "Is_Deleted")
    private boolean isDeleted;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Seller_User_Id")
    private Seller seller;


    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariations;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;



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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

   @JsonBackReference(value = "product-category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(List<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }


    public List<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }
}
