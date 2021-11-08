package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import com.ttn.project.ecommerce.exceptions.CategoryNotFoundException;
import com.ttn.project.ecommerce.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public String createCategory(CategoryHelper category){
        String categoryName = category.getName();
        Long parentCategory = category.getParentId();

        Category parent = null;

        boolean flag;

        if (parentCategory != 0){
            if (categoryRepository.findById(parentCategory).get() != null){
                parent = categoryRepository.findById(parentCategory).get();
            }
        }

        Optional<Category> category1 =
                categoryRepository.findByName(categoryName);

        if (category1.isPresent())
            return "category already exists";
        else {
            Category category2 = new Category();
            category2.setName(categoryName);
            category2.setParentCategory(parent);
            categoryRepository.save(category2);
            return "category created";
        }
    }

    public Category viewCategory(Long categoryId){
        Optional<Category> categoryById = categoryRepository.findById(categoryId);
        if (categoryById.isPresent()) {
            Category category = categoryById.get();
            return category;
        }
        else
            throw new CategoryNotFoundException("Category not found with id " + categoryId);
    }

    public List<Category> viewAllCategories(){
        return (List<Category>) categoryRepository.findAll();
    }

    public String updateCategory(){
        return "category updated successfully";
    }

}
