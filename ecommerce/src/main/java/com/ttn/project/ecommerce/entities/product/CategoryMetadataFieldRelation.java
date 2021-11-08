package com.ttn.project.ecommerce.entities.product;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY_METADATA_FIELD_VALUES")
public class CategoryMetadataFieldRelation {

    @EmbeddedId
    private CategoryMetadataFieldKey id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @MapsId("categoryMetadataFieldId")
    @JoinColumn(name = "CATEGORY_METADATA_FIELD_ID")
    private CategoryMetadataField categoryMetadataField;

    //values is a reserved words
    private String valuess;

}
