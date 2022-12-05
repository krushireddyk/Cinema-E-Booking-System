package com.cinemaeBooking.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingID;
	private float totalPrice;
	private Integer numberOfTickets;
	//private Integer children=0;
	//private Integer senior=0;
	//private Integer adult=0;
    //@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "promotionID", referencedColumnName = "promotionID")
    @ManyToOne(optional = true)
	@JoinColumn(name ="promotionID")
	private Promotion promotion;
    @JoinColumns({
        @JoinColumn(name="showDate", referencedColumnName="showDate"),
        @JoinColumn(name="showTime", referencedColumnName="showTime"),
        @JoinColumn(name="screenID", referencedColumnName="screenID")
    })
    //@OneToOne
    @ManyToOne(optional = true)
	private ShowDetails showdetails;
    //@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "paymentID", referencedColumnName = "paymentID")
    @ManyToOne(optional = true)
	@JoinColumn(name ="paymentID")
	private PaymentCard paymentCard;
    //@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = true)
	@JoinColumn(name ="userID")
	private User user;

    @ManyToOne(optional = true)
	@JoinColumn(name ="movieID")
    private Movie movie;

    @Transient
    private RStatus rStatus;

    public Integer getBookingID() {
        return this.bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNumberOfTickets() {
        return this.numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    /*public Integer getChildren() {
        return this.children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Integer getSenior() {
        return this.senior;
    }

    public void setSenior(Integer senior) {
        this.senior = senior;
    }

    public Integer getAdult() {
        return this.adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }*/

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Promotion getPromotion() {
        return this.promotion;
    }


    public ShowDetails getShowdetails() {
        return this.showdetails;
    }

    public void setShowdetails(ShowDetails showdetails) {
        this.showdetails = showdetails;
    }

    public PaymentCard getPaymentCard() {
        return this.paymentCard;
    }

    public User getUser() {
        return this.user;
    }
    
    public Movie getMovie() {
        return this.movie;
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

}
