package com.ttn.project.ecommerce.entities.order;

import com.ttn.project.ecommerce.entities.product.ProductVariation;

import javax.persistence.*;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "Order_Id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "Product_Variation_Id")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;
}
