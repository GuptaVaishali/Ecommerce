package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.exceptions.ProductNotFoundException;
import com.ttn.project.ecommerce.exceptions.ProductVariationNotFoundException;
import com.ttn.project.ecommerce.repos.ProductRepository;
import com.ttn.project.ecommerce.repos.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariationService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    public String createProductVariation(ProductVariation productVariation){
        Product product = productVariation.getProduct();
        Optional<Product> productById = productRepository.findById(product.getId());
        if (!productById.isPresent())
            return "product id is not valid";
        Product product1 = productById.get();
        productVariation.setProduct(product1);
        productVariationRepository.save(productVariation);
        return "product variation created successfully";
    }

    public ProductVariation viewProductVariation(long productVariationId){

        Optional<ProductVariation> productVariationById =
                productVariationRepository.findById(productVariationId);
        if (!productVariationById.isPresent())
            throw new ProductVariationNotFoundException("Product variation not found with id = " +
                    productVariationId);
        ProductVariation productVariation = productVariationById.get();

        return productVariation;
    }

    public List<ProductVariation> viewAllProductVariations(long productId){

        Optional<Product> productById = productRepository.findById(productId);
        if(!productById.isPresent())
            throw new ProductNotFoundException("Product id is not valid");
        List<ProductVariation> allById =
                (List<ProductVariation>) productVariationRepository.findAllById(productId);

        return allById;
    }

    public String updateProductVariation(long productVariationId){
        Optional<ProductVariation> productVariationById =
                productVariationRepository.findById(productVariationId);
        if (!productVariationById.isPresent())
            throw new ProductVariationNotFoundException("Product variation not found with id " +
                    productVariationId);
        ProductVariation productVariation = productVariationById.get();
        productVariationRepository.save(productVariation);
        return "Product variation updated successfully";
    }

}
