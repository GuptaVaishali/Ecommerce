package com.ttn.project.ecommerce.entities.product;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORY_METADATA_FIELD_VALUES")
public class CategoryMetadataFieldRelation {

    @EmbeddedId
    private CategoryMetadataFieldKey id = new CategoryMetadataFieldKey();

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @MapsId("categoryMetadataFieldId")
    @JoinColumn(name = "CATEGORY_METADATA_FIELD_ID")
    private CategoryMetadataField categoryMetadataField;

    //values is a reserved words
    private String value;


    //getters and setters
    public CategoryMetadataFieldKey getId() {
        return id;
    }

    public void setId(CategoryMetadataFieldKey id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
