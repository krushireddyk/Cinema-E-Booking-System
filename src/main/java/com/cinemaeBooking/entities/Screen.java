package com.cinemaeBooking.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="screen")
public class Screen {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String screenID;
    private Integer numberOfSeats;

    //@OneToOne(mappedBy = "screen", cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "screen")
    private Set<ShowDetails> showdetailsList;

    public String getScreenID() {
        return this.screenID;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public Integer getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
}
