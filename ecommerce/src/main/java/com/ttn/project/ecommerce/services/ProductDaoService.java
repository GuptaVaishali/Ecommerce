package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.entities.product.ProductVariation;
import com.ttn.project.ecommerce.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDaoService {

    @Autowired
    ProductRepository productRepository;

    public String addProduct(Product product){

        return "product added successfully";
    }

    public Product viewProduct(long productId){
        Optional<Product> productById = productRepository.findById(productId);
        Product product = productById.get();
        return product;
    }

    public List<Product> viewAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public String updateProduct(long productId){
        Optional<Product> productById = productRepository.findById(productId);
        Product product = productById.get();
        return "Product updated successfully";
    }

    public String deleteProduct(long productId){
        return "product deleted successfully";
    }

}
