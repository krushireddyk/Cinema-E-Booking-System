package com.cinemaeBooking.entities;

import java.util.List;

public class BookingList {
    private List<Booking> bookingList;
    private RStatus rStatus;

    public List<Booking> getBookingList() {
        return this.bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }
    

    public RStatus getRStatus() {
        return this.rStatus;
    }

    public void setRStatus(RStatus rStatus) {
        this.rStatus = rStatus;
    }

}

    
