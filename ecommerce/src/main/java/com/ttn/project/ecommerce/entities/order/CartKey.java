package com.ttn.project.ecommerce.entities.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartKey implements Serializable {
    @Column(name = "CUSTOMER_ID")
    private long customerId;

    @Column(name = "PRODUCT_VARIATION_ID")
    private long productVariationId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartKey cartKey = (CartKey) o;
        return customerId == cartKey.customerId && productVariationId == cartKey.productVariationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productVariationId);
    }
}
