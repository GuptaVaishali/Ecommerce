package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.services.ProductDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductDaoService productDaoService;

    @PostMapping("add-product")
    public String addProduct(Product product){

        return productDaoService.addProduct(product);
    }

    @GetMapping("view-product/{productId}")
    public Product viewProduct(long productId){
        return productDaoService.viewProduct(productId);
    }

    @GetMapping("view-all-products")
    public List<Product> viewAllProducts(){
        return productDaoService.viewAllProducts();
    }

    @PatchMapping("/update-product/{productId}")
    public String updateProduct(long productId){

        return productDaoService.updateProduct(productId);
    }

    @DeleteMapping("/delete-product/{productId}")
    public String deleteProduct(long productId){
        return "product deleted successfully";
    }
}
