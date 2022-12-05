package com.cinemaeBooking.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="showdetails")
public class ShowDetails {
    @EmbeddedId
    private ShowId showId = new ShowId();
    private Integer ShowDuration;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movieID", referencedColumnName = "movieID")
    private Movie movie;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "showdetails")
    private Set<Booking> bookingList;

    @Transient
    private RStatus rStatus;

	//@JoinColumn(name ="screenID",referencedColumnName="screenID")
    //@MapsId("screenID")
    //@ManyToOne
    //@JoinColumn(name = "screenID",insertable = false, updatable = false)
    //private Screen screen;

    /*public Screen getScreen() {
        return this.screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }*/

    public ShowId getShowId() {
        return this.showId;
    }

    public void setShowId(ShowId showId) {
        this.showId = showId;
    } 

    public Integer getShowDuration() {
        return this.ShowDuration;
    }

    public void setShowDuration(Integer ShowDuration) {
        this.ShowDuration = ShowDuration;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public RStatus getRStatus() {
        return this.rStatus;
    }

    public void setRStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }

    /*public Set<Booking> getBookingList() {
        return this.bookingList;
    }

    public void setBookingList(Set<Booking> bookingList) {
        this.bookingList = bookingList;
    }*/


}
