package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.product.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

    Optional<Category> findByName(String categoryName);
}
