package com.ttn.project.ecommerce.entities.product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoryMetadataFieldKey implements Serializable {

    @Column(name = "CATEGORY_ID")
    private long categoryId;

    @Column(name = "CATEGORY_METADATA_FIELD_ID")
    private long categoryMetadataFieldId;

    public CategoryMetadataFieldKey() {
    }

    public CategoryMetadataFieldKey(long categoryId, long categoryMetadataFieldId) {
        this.categoryId = categoryId;
        this.categoryMetadataFieldId = categoryMetadataFieldId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryMetadataFieldId() {
        return categoryMetadataFieldId;
    }

    public void setCategoryMetadataFieldId(long categoryMetadataFieldId) {
        this.categoryMetadataFieldId = categoryMetadataFieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryMetadataFieldKey that = (CategoryMetadataFieldKey) o;
        return categoryId == that.categoryId && categoryMetadataFieldId == that.categoryMetadataFieldId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryMetadataFieldId);
    }
}
