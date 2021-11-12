package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Optional<Product> findByName(String name);

    List<Product> findAllByCategory(Category category);

    List<Product> findByCategory(Category category);
}
