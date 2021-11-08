package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.product.CategoryMetadataField;
import com.ttn.project.ecommerce.services.MetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
