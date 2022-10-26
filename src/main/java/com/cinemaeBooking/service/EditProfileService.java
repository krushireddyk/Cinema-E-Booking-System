package com.cinemaeBooking.service;

import com.cinemaeBooking.entities.BillingAddress;
import com.cinemaeBooking.entities.HomeAddress;
import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.Status;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditProfileService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User saveUser(String userName, User updatedUser) {
        User user = userRepository.findByUserName(userName);
        if(updatedUser.getFirstName() != null)
            user.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        if(updatedUser.getEmailID() != null)
            user.setEmailID(updatedUser.getEmailID());
        if(updatedUser.getPhoneNumber() != null)
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        if(updatedUser.getPaymentCards() != null && !updatedUser.getPaymentCards().isEmpty()) {
            Set<PaymentCard> newPaymentCards = updatedUser.getPaymentCards();
            Set<PaymentCard> paymentCards = user.getPaymentCards();
            for(PaymentCard paymentCard : newPaymentCards) {
                if(paymentCard.getAddress() != null) {
                    System.out.println(paymentCard.getAddress().getCity());
                    System.out.println(paymentCard.getAddress().getState());
                    System.out.println(paymentCard.getAddress().getStreet());
                    System.out.println(paymentCard.getAddress().getZipCode());
                    BillingAddress billingAddress = paymentCard.getAddress();
                    billingAddress.setPaymentCard(paymentCard);
                    paymentCard.setAddress(billingAddress);
                }
                paymentCard.setUser(user);
                paymentCards.add(paymentCard);
            }
            //paymentCards.addAll(newPaymentCards);
            user.setPaymentCards(paymentCards);
        }
        if(updatedUser.getAddresses() != null && !updatedUser.getAddresses().isEmpty()) {
            Set<HomeAddress> newAddresses = updatedUser.getAddresses();
            Set<HomeAddress> addresses = user.getAddresses();
            for(HomeAddress newAddress : newAddresses) {
                newAddress.setUser(user);
                addresses.add(newAddress);
            }
            //addresses.addAll(newAddresses);
            user.setAddresses(addresses);
        }
        if(updatedUser.getStatus() != null && updatedUser.getStatus().getStatus() != null) {
            user.setStatus(updatedUser.getStatus());
        }
        if(updatedUser.getUsertype() != null && updatedUser.getUsertype().getUserRole() != null) {
            user.setUsertype(updatedUser.getUsertype());
        }
        return userRepository.save(user);
    }

    @Transactional
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }
}
