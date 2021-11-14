package com.ttn.project.ecommerce.entities.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ttn.project.ecommerce.auditing.Auditable;
import com.ttn.project.ecommerce.entities.order.Cart;
import com.ttn.project.ecommerce.entities.order.OrderProduct;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ProductVariation extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(value = 0, message = "Quantity should be greater than 0")
    @NotNull
    private int quantityAvailable;

    @Min(value = 0, message = "Price should be greater than 0")
    @NotNull
    private double price;

    @JsonProperty
    private boolean isActive;

    // how to store json?
    @Column(columnDefinition = "JSON")
    private String metadata;

    //String or byte[] ?
    private String primaryImageName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Product product;

    //mapping for order_product table

    @OneToMany(mappedBy = "order")
    List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productVariation")
    private List<Cart> cartList;


    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
