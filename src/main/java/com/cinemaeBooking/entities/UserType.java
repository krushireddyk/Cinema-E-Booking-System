package com.cinemaeBooking.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="usertype")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer RoleID;
    private String userRole;

    @OneToOne(mappedBy = "usertype")
    private User user;

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
