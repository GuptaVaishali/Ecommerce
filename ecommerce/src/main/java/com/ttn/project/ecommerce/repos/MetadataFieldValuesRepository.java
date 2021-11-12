package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldKey;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldRelation,
        CategoryMetadataFieldKey> {

    List<CategoryMetadataFieldRelation> findByCategoryId(long catId);
}
