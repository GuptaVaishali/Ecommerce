package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.services.ProductVariationService;
import com.ttn.project.ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductVariationController {

    @Autowired
    ProductVariationService productVariationService;

    @Autowired
    UserDaoService userDaoService;

    @PostMapping("/create-product-variation")
    public String createProductVariation(@Valid @RequestBody ProductVariation productVariation){

        return productVariationService.createProductVariation(productVariation);
    }

    @GetMapping("/view-product-variation/{productVariationId}")
    public ProductVariation viewProductVariation(@PathVariable long productVariationId){
        Seller seller = userDaoService.getLoggedInSeller();
        long sellerId = seller.getId();
        return productVariationService.viewProductVariation(sellerId,productVariationId);
    }

    @GetMapping("/view-all-product-variations/{productId}")
    public List<ProductVariation> viewAllProductVariations(@PathVariable long productId){

      return productVariationService.viewAllProductVariations(productId);
    }

    @PutMapping("/update-product-variation/{productVariationId}")
    public String updateProductVariation(@PathVariable long productVariationId,
                                         @RequestBody ProductVariation productVariation){

        return productVariationService.updateProductVariation(productVariationId, productVariation);
    }

}
