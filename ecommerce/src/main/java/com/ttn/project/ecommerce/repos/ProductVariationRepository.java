package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.product.ProductVariation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {

    List<ProductVariation> findAllById(long productId);
}
