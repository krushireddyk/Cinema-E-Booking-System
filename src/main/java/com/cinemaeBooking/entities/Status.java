package com.cinemaeBooking.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="status")
public class Status {
    @Id
    private Integer statusID;

    private String status;

    @OneToOne(mappedBy = "status")
    private User user;

    public Integer getStatusID() {
        return this.statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
