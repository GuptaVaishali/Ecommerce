package com.ttn.project.ecommerce.entities.registration;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String authority;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "User_Role",
//                joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"),
//                inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "id"))
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch =FetchType.EAGER )
    private List<User> users;

    //getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
