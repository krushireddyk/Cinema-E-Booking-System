package com.cinemaeBooking.entities;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="showdetails")
public class ShowDetails {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ShowID;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ShowDate;
    private LocalTime ShowTime;
    private Integer ShowDuration;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movieID", referencedColumnName = "movieID")
    private Movie movie;

    //@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "screenID", referencedColumnName = "screenID")
    @ManyToOne(optional = true)
	@JoinColumn(name ="screenID")
    private Screen screen;

    public Integer getShowID() {
        return this.ShowID;
    }

    public void setShowID(Integer ShowID) {
        this.ShowID = ShowID;
    }

    public Date getShowDate() {
        return this.ShowDate;
    }

    public void setShowDate(Date ShowDate) {
        this.ShowDate = ShowDate;
    }

    public LocalTime getShowTime() {
        return this.ShowTime;
    }

    public void setShowTime(LocalTime ShowTime) {
        this.ShowTime = ShowTime;
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

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return this.screen;
    }

}
