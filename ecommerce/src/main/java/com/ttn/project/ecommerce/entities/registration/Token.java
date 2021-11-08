package com.ttn.project.ecommerce.entities.registration;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Token {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "Token_ID")
    Long id;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "TOKEN",unique = true)
    private String token;

    @Column(name = "FORGOT_PASS_TOKEN",unique = true)
    private String forgotPassToken;

    @CreationTimestamp
    @Column(name = "TIME_STAMP",updatable = false)
    private Timestamp timeStamp;

    @Column(name = "EXPIRE_AT",updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt;

   // @OneToOne(cascade = CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Transient
    private boolean isExpired;

    public Token(){

    }

    public Token(User user) {
        this.user = user;
    }

    public boolean isExpired() {
        // this is generic implementation, you can always make it timezone specific
        return getExpireAt().isBefore(LocalDateTime.now());

    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String getForgotPassToken() {
        return forgotPassToken;
    }

    public void setForgotPassToken(String forgotPassToken) {
        this.forgotPassToken = forgotPassToken;
    }
}
