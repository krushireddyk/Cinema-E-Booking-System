package com.cinemaeBooking.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="paymentcard")
public class PayCard 
{
	@Id
	private int payCard_id;
	private String cartType;
	private String cardNumber;
	private String expiryDate;
	private String cvv;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
	public int getPayCard_id() {
		return payCard_id;
	}
	public void setPayCard_id(int payCard_id) {
		this.payCard_id = payCard_id;
	}
	public String getCartType() {
		return cartType;
	}
	public void setCartType(String cartType) {
		this.cartType = cartType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
