package com.ttn.project.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductVariationUpdateDto {

    @Min(value = 0, message = "Quantity should be greater than 0")
    private int quantityAvailable;

    @Min(value = 0, message = "Price should be greater than 0")
    private double price;

    @JsonProperty
    private boolean isActive;

    @Column(columnDefinition = "JSON")
    private String metadata;

    private String primaryImageName;


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

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }
}
