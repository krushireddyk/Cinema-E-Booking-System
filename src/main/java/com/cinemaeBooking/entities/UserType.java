package com.cinemaeBooking.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="usertype")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer RoleID;
    private String userRole;

    //@OneToOne(mappedBy = "usertype", cascade=CascadeType.ALL, orphanRemoval = true)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usertype")
    private Set<User> users;

    public Integer getRoleID() {
        return this.RoleID;
    }

    public void setRoleID(Integer roleID) {
        this.RoleID = roleID;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}