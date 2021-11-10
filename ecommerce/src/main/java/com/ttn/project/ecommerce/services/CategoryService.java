package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import com.ttn.project.ecommerce.entities.product.Product;
import com.ttn.project.ecommerce.exceptions.CategoryNotFoundException;
import com.ttn.project.ecommerce.repos.CategoryRepository;
import com.ttn.project.ecommerce.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    public String createCategory(CategoryHelper category){
        String categoryName = category.getName();
        long parentCategory = category.getParentId();

        System.out.println(">>>>>>>>>> parentCategory " + parentCategory +
                "  categoryName   " + categoryName);
        Category parent = null;

        // parent category should not exist with any product
//        Optional<Product> product =
//                productRepository.findByCategory(categoryRepository.findByName(categoryName).get());
//        if (product.isPresent())
//            return "Please do not insert leaf node category";

        // if parent category exists that is not null
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

    public String createCategory1(Category category){
        String categoryName = category.getName();

        Optional<Category> category1 =
                categoryRepository.findByName(categoryName);

        if (category1.isPresent())
            return "category already exists";
        else {
            categoryRepository.save(category);
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

    public String updateCategory(long categoryId, String categoryName){
        Optional<Category> categoryById = categoryRepository.findById(categoryId);
        if (!categoryById.isPresent()) {
            throw new CategoryNotFoundException("Category not found with id " + categoryId);
        }
        Category category = categoryById.get();
        Optional<Category> categoryByName = categoryRepository.findByName(categoryName);
        if (categoryByName.isPresent())
            return "category name already exists";
        category.setName(categoryName);
        categoryRepository.save(category);
        return "category updated successfully";
    }

}
