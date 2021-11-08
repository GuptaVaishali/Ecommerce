package com.ttn.project.ecommerce.entities.registration;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ttn.project.ecommerce.auditing.Auditable;
import com.ttn.project.ecommerce.validations.Password;
import com.ttn.project.ecommerce.validations.PasswordMatches;
import com.ttn.project.ecommerce.validations.UniqueEmail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "USER")
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@PasswordMatches
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Email")
    @Email
    @NotNull
    @NotEmpty
    @UniqueEmail
    private String email;

    @Column(name = "FiRST_NAME")
    @NotNull
    @NotEmpty
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    @NotNull
    @NotEmpty
    private String lastName;

    @Column(name = "PASSWORD")
    @NotNull
    @Password
    private String password;

    @Transient
    @NotNull
    @Password
    private String confirmPassword;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "IS_EXPIRED")
    private boolean isExpired;

    @Column(name = "IS_LOCKED")
    private boolean isLocked;

    @Column(name = "INVALID_ATTEMPT_COUNT")
    private int invalidAttemptCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;

    //@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch =FetchType.EAGER )
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "User_Role",
                joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "ROlE_ID", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToOne(mappedBy = "user")
    private Token token;

    public User() {
    }

    public User(User user) {
        this.id=user.id;
        this.email=user.email;
        this.firstName=user.firstName;
        this.middleName=user.middleName;
        this.lastName=user.lastName;
        this.password=user.password;
        this.isDeleted=user.isDeleted;
        this.isActive=user.isActive;
        this.roles=user.roles;
    }

//    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getInvalidAttemptCount() {
        return invalidAttemptCount;
    }

    public void setInvalidAttemptCount(int invalidAttemptCount) {
        this.invalidAttemptCount = invalidAttemptCount;
    }

    @JsonManagedReference
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
