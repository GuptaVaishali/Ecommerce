package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {

}
