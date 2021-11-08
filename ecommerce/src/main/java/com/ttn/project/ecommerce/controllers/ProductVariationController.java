package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;

    @PostMapping("create-product-variation")
    public String createProductVariation(ProductVariation productVariation){

        return productVariationService.createProductVariation(productVariation);
    }

    @GetMapping("view-product-variation")
    public ProductVariation viewProductVariation(long productVariationId){

        return productVariationService.viewProductVariation(productVariationId);
    }

    @GetMapping("view-all-product-variations")
    public List<ProductVariation> viewAllProductVariations(long productId){

      return productVariationService.viewAllProductVariations(productId);
    }

    public String updateProductVariation(long productVariationId){

        return productVariationService.updateProductVariation(productVariationId);
    }

}
