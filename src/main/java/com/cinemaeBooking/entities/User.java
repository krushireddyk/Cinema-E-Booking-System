package com.cinemaeBooking.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name="user")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String emailID;
    private String phoneNumber;
    //private boolean enabled;
    //private String optPromotionalEmails = "false";
    //private String verificationCode;

	//@OneToMany(mappedBy = "user")
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
    private Set<PaymentCard> paymentCards;
    
    //@OneToMany
    //private Set<Order> orders = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<HomeAddress> addresses = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "roleID")
	private UserType usertype;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "statusID")
	private Status status;
    
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailID() {
		return this.emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/*public boolean getEnabled() {
		return this.enabled;
	}*/

	public Set<PaymentCard> getPaymentCards() {
		return this.paymentCards;
	}

	public void setPaymentCards(Set<PaymentCard> paymentCards) {
		this.paymentCards = paymentCards;
	}

	public Set<HomeAddress> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<HomeAddress> addresses) {
		this.addresses = addresses;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/*public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}*/
	/*public String getOptPromotionalEmails() {
		return optPromotionalEmails;
	}
	public void setOptPromotionalEmails(String optPromotionalEmails) {
		this.optPromotionalEmails = optPromotionalEmails;
	}*/

	/*public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}*/

	public UserType getUsertype() {
		return this.usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}