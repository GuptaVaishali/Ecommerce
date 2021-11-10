package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldKey;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldRelation;
import com.ttn.project.ecommerce.entities.registration.Customer;
import com.ttn.project.ecommerce.entities.registration.Role;
import com.ttn.project.ecommerce.exceptions.CategoryNotFoundException;
import com.ttn.project.ecommerce.repos.CategoryRepository;
import com.ttn.project.ecommerce.repos.MetadataFieldRepository;
import com.ttn.project.ecommerce.repos.MetadataFieldValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MetadataFieldService {

    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

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

    public String createMetadataFieldValues(long categoryId, long metadataFieldId,
                                            MetadataFieldValueHelper metadataFieldValues){
        Set<String> fieldValues = metadataFieldValues.getValueSet();
        fieldValues.forEach(System.out::println);

        Optional<Category> category = categoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField = metadataFieldRepository.
                findById(metadataFieldId);
        if (!category.isPresent())
            return "category not found with category id = " + categoryId;
        else if (!categoryMetadataField.isPresent())
            return "category metadata field not found with id = " + metadataFieldId;
        else {
            System.out.println("inside else statement");
            Category category1 = category.get();
            CategoryMetadataField categoryMetadataField1 = categoryMetadataField.get();

            CategoryMetadataFieldRelation metadataFieldValues1 = new CategoryMetadataFieldRelation();

            metadataFieldValues1.setCategoryMetadataField(categoryMetadataField1);
            metadataFieldValues1.setCategory(category1);

            String delim = ",";
            String metaFieldvalues = String.join(delim, fieldValues);

            System.out.println(metaFieldvalues);

            metadataFieldValues1.setValue(metaFieldvalues);

            metadataFieldValuesRepository.save(metadataFieldValues1);
            return "category metadata field values saved successfully";
        }
    }

    public String updateMetadataFieldValues(long categoryId, long metadataFieldId,
                                            MetadataFieldValueHelper metadataFieldValues){
        Set<String> fieldValues = metadataFieldValues.getValueSet();
        fieldValues.forEach(System.out::println);


        Optional<Category> category = categoryRepository.findById(categoryId);
        Optional<CategoryMetadataField> categoryMetadataField = metadataFieldRepository.
                findById(metadataFieldId);

        CategoryMetadataFieldKey categoryMetadataFieldKey = new CategoryMetadataFieldKey(categoryId,
                metadataFieldId);
        Optional<CategoryMetadataFieldRelation> categoryMetadataFieldRelation =
                metadataFieldValuesRepository.findById(categoryMetadataFieldKey);
        if (!category.isPresent())
            return "category not found with category id = " + categoryId;
        if (!categoryMetadataField.isPresent())
            return "category metadata field not found with id = " + metadataFieldId;
        if (!categoryMetadataFieldRelation.isPresent())
            return "category meta data field value with this category id " + categoryId +
                    " and field Id " + metadataFieldId + " does not exist";

        else {
            CategoryMetadataFieldRelation metadataFieldValues1 = categoryMetadataFieldRelation.get();

            String delim = ",";
            String metaFieldvalues = String.join(delim, fieldValues);

            System.out.println(metaFieldvalues);

            metadataFieldValues1.setValue(metaFieldvalues);

            metadataFieldValuesRepository.save(metadataFieldValues1);
            return "category metadata field values updated successfully";
        }
    }

}
