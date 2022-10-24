/*package com.cinemaeBooking.entities;

import java.util.HashSet;
import java.util.Set;

public class Order {

	private int order_id;
	private String userName;
	private float totalPrice;
	private int numberOfTickets;
	private int children=0;
	private int senior=0;
	private int adult=0;
	private String promoCode;
	private Show show;
	private Promotion promotion;
	private PayCard payCard;
	private User user;
	
	private Set<Ticket> tickets = new HashSet<>();

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getSenior() {
		return senior;
	}

	public void setSenior(int senior) {
		this.senior = senior;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public PayCard getPayCard() {
		return payCard;
	}

	public void setPayCard(PayCard payCard) {
		this.payCard = payCard;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public int getChildrenTickets() {
		int childrenTickets = 0;
		for(Ticket t : tickets)
		{
			if(t.getAge() == TicketType.CHILD)
			childrenTickets = childrenTickets + 1;
        }
		return childrenTickets;
		}
	
	public int getSeniorTickets() {
		int seniorTickets = 0;
		for(Ticket t : tickets)
		{
			if(t.getAge() == TicketType.CHILD)
				seniorTickets = seniorTickets + 1;
        }
		return seniorTickets;
		}
	
	public int getAdultTickets() {
		int adultTickets = 0;
		for(Ticket t : tickets)
		{
			if(t.getAge() == TicketType.CHILD)
				adultTickets = adultTickets + 1;
        }
		return adultTickets;
		}
	
}*/
	

