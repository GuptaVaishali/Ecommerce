package com.ttn.project.ecommerce.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORY_METADATA_FIELD")
public class CategoryMetadataField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "categoryMetadataField")
    private List<CategoryMetadataFieldRelation> metaDataFieldValues;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<CategoryMetadataFieldRelation> getMetaDataFieldValues() {
        return metaDataFieldValues;
    }

    public void setMetaDataFieldValues(List<CategoryMetadataFieldRelation> metaDataFieldValues) {
        this.metaDataFieldValues = metaDataFieldValues;
    }
}
