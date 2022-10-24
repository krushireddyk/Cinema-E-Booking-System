package com.cinemaeBooking.entities;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Inheritance( strategy = InheritanceType.JOINED )
public class Customer 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userID", unique = true)
	private int userID;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@Column(name = "userName")
    private String userName;
	
	@Column(name = "emailId")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@Column(name="role")
	private String role;
	
	@Column(name = "statusID")
	private String accountStatus;
	
	@Column(name = "paymentcard")
	private String paymentCard;
	
	@Transient
	private String address;
	
	@Transient
	private String city;
	
	@Transient
	private String state;
	
	@Transient
	private String zipcode;
	
	@Transient
	private Boolean isSubscribed;
	
	@Transient
	private Boolean isUnSubscribed;
	
	public Customer()
	{
		
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getPaymentCard() {
		return paymentCard;
	}
	public void setPaymentCard(String paymentCard) {
		this.paymentCard = paymentCard;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public Boolean getIsSubscribed() {
		return isSubscribed;
	}
	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	public Boolean getIsUnSubscribed() {
		return isUnSubscribed;
	}
	public void setIsUnSubscribed(Boolean isUnSubscribed) {
		this.isUnSubscribed = isUnSubscribed;
	}
	
	
}
