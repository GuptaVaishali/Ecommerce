package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.services.ProductDaoService;
import com.ttn.project.ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductDaoService productDaoService;

    @Autowired
    UserDaoService userDaoService;


    @PostMapping("/add-product")
    public String addProduct(@RequestBody Product product){

        Seller seller = userDaoService.getLoggedInSeller();
        long sellerId = seller.getId();
        return productDaoService.addProduct(sellerId,product);
    }

    @GetMapping("/view-product/{productId}")
    public Product viewProduct(@PathVariable long productId){
        return productDaoService.viewProduct(productId);
    }

    @GetMapping("/view-all-products")
    public List<Product> viewAllProducts(){
        return productDaoService.viewAllProducts();
    }

    @PutMapping("/update-product/{productId}")
    public String updateProduct(@PathVariable long productId, @RequestBody Product product){

        return productDaoService.updateProduct(productId,product);
    }

    @DeleteMapping("/delete-product/{productId}")
    public String deleteProduct(@PathVariable long productId){
        return productDaoService.deleteProduct(productId);
    }

    @PutMapping("/deactivate-product/{productId}")
    public String deActivateProduct(@PathVariable long productId){

        return productDaoService.deActivateProduct(productId);
    }

    @PutMapping("/activate-product/{productId}")
    public String activateProduct(@PathVariable long productId){

        return productDaoService.activateProduct(productId);
    }


    @GetMapping("/similar-products/{productId}")
    public List<Product> viewSimilarProducts(@PathVariable long productId){
        return productDaoService.viewSimilarProducts(productId);
    }
}
