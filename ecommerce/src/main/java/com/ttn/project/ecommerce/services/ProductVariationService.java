package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.repos.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    public String createProductVariation(ProductVariation productVariation){

        return "product variation created successfully";
    }

    public ProductVariation viewProductVariation(long productVariationId){

        Optional<ProductVariation> productVariationById =
                productVariationRepository.findById(productVariationId);
        ProductVariation productVariation = productVariationById.get();


        return productVariation;
    }

    public List<ProductVariation> viewAllProductVariations(long productId){

        List<ProductVariation> allById =
                (List<ProductVariation>) productVariationRepository.findAllById(productId);

        return allById;
    }

    public String updateProductVariation(long productVariationId){
        Optional<ProductVariation> productVariation =
                productVariationRepository.findById(productVariationId);
        return "Product variation updated successfully";
    }

}
