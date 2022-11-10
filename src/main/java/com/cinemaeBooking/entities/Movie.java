package com.cinemaeBooking.entities;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name="movie")
public class Movie {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieID;
    private String title;
    private String category;
    private String movieCast;
    private String director;
    private String producer;
    private String synopsis;
    private String review;
    private String trailerLink;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[]  picture;
    private Integer rating;
    @Transient
    private RStatus rStatus;

    public Integer getMovieID() {
        return this.movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMovieCast() {
        return this.movieCast;
    }

    public void setMovieCast(String movieCast) {
        this.movieCast = movieCast;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTrailerLink() {
        return this.trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }    

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public RStatus getRStatus() {
        return this.rStatus;
    }

    public void setRStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }

}
