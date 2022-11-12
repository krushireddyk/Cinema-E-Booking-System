package com.cinemaeBooking.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="screen")
public class Screen {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer screenID;
    private Integer numberOfSeats;

    @OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "showID", referencedColumnName = "showID")
    private ShowDetails showdetails;

    public Integer getScreenID() {
        return this.screenID;
    }

    public void setScreenID(Integer screenID) {
        this.screenID = screenID;
    }

    public Integer getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setShowdetails(ShowDetails showdetails) {
        this.showdetails = showdetails;
    }
    
}
