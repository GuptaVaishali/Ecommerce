package com.ttn.project.ecommerce.entities.order;

import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.entities.registration.Customer;

import javax.persistence.*;

@Entity
public class Cart {

    @EmbeddedId
    private CartKey id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "CUSTOMER_USER_ID")
    private Customer customer;

    @ManyToOne
    @MapsId("productVariationId")
    @JoinColumn(name = "PRODUCT_VARIATION_ID")
    private ProductVariation productVariation;

    private int quantity;

    @Column(name = "IS_WISHLIST_ITEM")
    private boolean isWishlistItem;
}
