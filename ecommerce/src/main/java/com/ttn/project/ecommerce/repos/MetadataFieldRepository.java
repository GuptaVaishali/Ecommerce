package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MetadataFieldRepository extends PagingAndSortingRepository<CategoryMetadataField,Long> {

    Optional<CategoryMetadataField> findByName(String fieldName);
}
