package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldRelation;
import com.ttn.project.ecommerce.entities.registration.Customer;
import com.ttn.project.ecommerce.entities.registration.Role;
import com.ttn.project.ecommerce.repos.MetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetadataFieldService {

    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    public String createMetadataField(CategoryMetadataField categoryMetadataField){
        String fieldName = categoryMetadataField.getName();
        Optional<CategoryMetadataField> categoryMetadataField1 =
                metadataFieldRepository.findByName(fieldName);
        if (categoryMetadataField1.isPresent())
            return "category metadata field already exists";
        else {
            metadataFieldRepository.save(categoryMetadataField);
            return "Category Metadata Field created";
        }
    }

    public List<CategoryMetadataField> viewAllMetadataFields(){
        return (List<CategoryMetadataField>) metadataFieldRepository.findAll();
    }

}
