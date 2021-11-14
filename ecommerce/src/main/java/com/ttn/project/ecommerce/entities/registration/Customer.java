package com.ttn.project.ecommerce.entities.registration;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.ttn.project.ecommerce.validations.Phone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@JsonFilter("customerFilter")
public class Customer extends User {

    @Column(name = "CONTACT")
    @NotNull
    @NotEmpty
    @Phone
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
