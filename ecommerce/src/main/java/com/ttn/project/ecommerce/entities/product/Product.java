package com.ttn.project.ecommerce.entities.product;

import com.ttn.project.ecommerce.entities.registration.Seller;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;

    @Column(name = "Is_Cancellable")
    private boolean isCancellable;

    @Column(name = "Is_Returnable")
    private boolean isReturnable;

    private String Brand;

    @Column(name = "Is_Active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "Seller_User_Id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariations;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;
}
