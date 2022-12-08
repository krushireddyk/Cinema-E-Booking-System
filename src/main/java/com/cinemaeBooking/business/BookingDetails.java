package com.cinemaeBooking.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemaeBooking.entities.Booking;
import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.service.BookingService;
import com.cinemaeBooking.service.EncryptDecrypt;

@Service
public class BookingDetails {

    @Autowired
    BookingService bookingService;

    @Autowired
    EncryptDecrypt encryptDecrypt;

    public Booking submitOrder(Booking booking) throws Exception {
        if(booking.getIsPaymentCardNew() == 0) {
            PaymentCard paymentCard = booking.getPaymentCard();
            paymentCard.setCardNumber(encryptDecrypt.encrypt(paymentCard.getCardNumber()));
            booking.setPaymentCard(paymentCard);
        }
        return bookingService.submitOrder(booking);
    }

    public List<Booking> findAllBookingsByUserName(String userName) {
        List<Booking> bookingList = new ArrayList<Booking>();
        bookingList = bookingService.findAllBookingsByUserName(userName);
        List<Booking> finalBookingList = new ArrayList<Booking>();
        int count = 0;
        for(Booking booking : bookingList) {
            Booking tempBooking = new Booking();
            PaymentCard paymentCardDecrypted = new PaymentCard();
            if(booking.getPaymentCard() != null) {
                paymentCardDecrypted.setAddress(booking.getPaymentCard().getAddress());
                if(booking.getPaymentCard().getCardNumber() != null) {
                    paymentCardDecrypted.setCardNumber(encryptDecrypt.decrypt(booking.getPaymentCard().getCardNumber()));
                }
                paymentCardDecrypted.setExpiryDate(booking.getPaymentCard().getExpiryDate());
                paymentCardDecrypted.setPaymentID(booking.getPaymentCard().getPaymentID());
                tempBooking.setPaymentCard(paymentCardDecrypted);
            }
            if(count == 0) {
                User user = booking.getUser();
                user.setPassword(encryptDecrypt.decrypt(user.getPassword()));
                tempBooking.setUser(user);
                Set<PaymentCard> decryptedPaymentCards = new HashSet<PaymentCard>();
                for(PaymentCard paymentCard : user.getPaymentCards()) {
                    PaymentCard decryptedCard = paymentCard;
                    decryptedCard.setCardNumber(encryptDecrypt.decrypt(paymentCard.getCardNumber()));
                    decryptedPaymentCards.add(decryptedCard);
                }
                user.setPaymentCards(decryptedPaymentCards);
                count++;
            }
            else {
                tempBooking.setUser(booking.getUser());
            }
            tempBooking.setBookingID(booking.getBookingID());
            tempBooking.setMovie(booking.getMovie());
            tempBooking.setNumberOfTickets(booking.getNumberOfTickets());
            tempBooking.setPromotion(booking.getPromotion());
            tempBooking.setShowdetails(booking.getShowdetails());
            tempBooking.setTotalPrice(booking.getTotalPrice());
            finalBookingList.add(tempBooking);
        }
        return finalBookingList;
    }
}
