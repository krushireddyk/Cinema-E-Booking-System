package com.cinemaeBooking.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="paymentcard")
public class PaymentCard 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentID;
	private String cardNumber;
	private String expiryDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

	//@OneToOne
	//@JoinColumn(name = "PaymentID", referenceColumnName = "PaymentID")
	@OneToOne(mappedBy = "paymentCard", cascade = CascadeType.ALL)
	private BillingAddress address;

	//@OneToOne(mappedBy = "paymentCard", cascade = CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "paymentCard")
	private Set<Booking> bookingList;

	public Integer getPaymentID() {
		return this.paymentID;
	}

	public void setPaymentID(Integer paymentID) {
		this.paymentID = paymentID;
	}


	public String getCardNumber() {
		return this.cardNumber;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentCard )) return false;
        return paymentID != null && paymentID.equals(((PaymentCard) o).getPaymentID());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

	public void setUser(User user) {
		this.user = user;
	}

	public BillingAddress getAddress() {
		return this.address;
	}

	public void setAddress(BillingAddress address) {
		this.address = address;
	}

	/*public Set<Booking> getBookingList() {
		return this.bookingList;
	}

	public void setBookingList(Set<Booking> bookingList) {
		this.bookingList = bookingList;
	}*/
	
}


	
