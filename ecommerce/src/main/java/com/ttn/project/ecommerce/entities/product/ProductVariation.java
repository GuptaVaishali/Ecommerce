package com.ttn.project.ecommerce.entities.product;

import com.ttn.project.ecommerce.entities.order.Cart;
import com.ttn.project.ecommerce.entities.order.OrderProduct;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int quantityAvailable;
    private double price;
    private boolean isActive;

    // how to store json?
    private String metadata;

    //String or byte[] ?
   // private byte[] primaryImageName;

    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Product product;

    //mapping for order_product table

    @OneToMany(mappedBy = "order")
    List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productVariation")
    private List<Cart> cartList;
}
