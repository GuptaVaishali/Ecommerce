package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.Category;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import com.ttn.project.ecommerce.entities.product.CategoryMetadataFieldRelation;
import com.ttn.project.ecommerce.services.MetadataFieldService;
import com.ttn.project.ecommerce.services.MetadataFieldValueHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MetadataFieldController {

    @Autowired
    MetadataFieldService metadataFieldService;

    @PostMapping("/create-metadatafield")
    public String createMetadataField(@RequestBody CategoryMetadataField categoryMetadataField){
        return metadataFieldService.createMetadataField(categoryMetadataField);
    }

    @GetMapping("view-metadatafields")
    public List<CategoryMetadataField> viewAllMetadataFields(){
        return metadataFieldService.viewAllMetadataFields();
    }


    @PostMapping("create-metadatafieldvalue/{categoryId}/{metadataFieldId}")
    public String createMetadataFieldValues(@PathVariable long categoryId,
                                            @PathVariable long metadataFieldId,
                                            @RequestBody MetadataFieldValueHelper metadataFieldValues){
        return metadataFieldService.createMetadataFieldValues(categoryId, metadataFieldId,
                 metadataFieldValues);
    }

    @PutMapping("update-metadatafieldvalue/{categoryId}/{metadataFieldId}")
    public String updateMetadataFieldValues(@PathVariable long categoryId,
                                            @PathVariable long metadataFieldId,
                                            @RequestBody MetadataFieldValueHelper metadataFieldValues){
        return metadataFieldService.updateMetadataFieldValues(categoryId, metadataFieldId,
                metadataFieldValues);
    }
}
