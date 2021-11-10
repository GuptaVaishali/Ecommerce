package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.exceptions.CategoryNotFoundException;
import com.ttn.project.ecommerce.services.CategoryHelper;
import com.ttn.project.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create-category")
    public String createCategory(@RequestBody CategoryHelper category){
        return categoryService.createCategory(category);
    }

    @PostMapping("/create-category1")
    public String createCategory1(@RequestBody Category category){
        return categoryService.createCategory1(category);
    }

    @GetMapping("view-category/{categoryId}")
    public Category viewCategory(@PathVariable Long categoryId){
        return categoryService.viewCategory(categoryId);
    }

    @GetMapping("view-all-categories")
    public List<Category> viewAllCategories(){
        return categoryService.viewAllCategories();
    }

    @PutMapping("/update-category/{categoryId}/{categoryName}")
    public String updateCategory(@PathVariable long categoryId, @PathVariable String categoryName){
        return categoryService.updateCategory(categoryId, categoryName);
    }




}
