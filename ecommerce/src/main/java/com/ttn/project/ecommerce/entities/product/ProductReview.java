package com.ttn.project.ecommerce.entities.product;

import com.ttn.project.ecommerce.entities.registration.Customer;

import javax.persistence.*;

@Entity
public class ProductReview {

    @EmbeddedId
    private ProductReviewKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "CUSTOMER_USER_ID")
    private Customer customer;

    private String review;

    private int rating;
}
