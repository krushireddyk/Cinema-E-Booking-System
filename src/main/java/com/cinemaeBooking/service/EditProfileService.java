package com.cinemaeBooking.service;

import com.cinemaeBooking.entities.BillingAddress;
import com.cinemaeBooking.entities.HomeAddress;
import com.cinemaeBooking.entities.PaymentCard;
import com.cinemaeBooking.entities.User;
import com.cinemaeBooking.exception.CustomErrorsException;
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

    @Autowired
    EncryptDecrypt encryptDecrypt;

    @Transactional
    public User saveUser(String userName, User updatedUser) 
    {
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
        /*if(updatedUser.getAddresses() != null && !updatedUser.getAddresses().isEmpty()) {
            Set<HomeAddress> newAddresses = updatedUser.getAddresses();
            Set<HomeAddress> addresses = user.getAddresses();
            for(HomeAddress newAddress : newAddresses) {
                newAddress.setUser(user);
                addresses.add(newAddress);
            }
            //addresses.addAll(newAddresses);
            user.setAddresses(addresses);
        }*/
        if(updatedUser.getAddress() != null) {
            HomeAddress newAddress = updatedUser.getAddress();
            HomeAddress address = user.getAddress();
            //if there is no address saved
            if(address == null && newAddress != null) {
                newAddress.setUser(user);
                user.setAddress(newAddress);
            }
        }
        if(updatedUser.getStatus() != null && updatedUser.getStatus().getStatus() != null) {
            user.setStatus(updatedUser.getStatus());
        }
        /*if(updatedUser.getUsertype() != null && updatedUser.getUsertype().getUserRole() != null) {
            user.setUsertype(updatedUser.getUsertype());
        }*/
        return userRepository.save(user);
    }

    //@Transactional
    public User editUser(String userName, User updatedUser) throws Exception{
        User user = userRepository.findByUserName(userName);
        User editedUser = null;
        if(updatedUser.getFirstName() != null)
            user.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        if(updatedUser.getPassword() != null) {
            String currentPassword = encryptDecrypt.decrypt(user.getPassword());
            if(currentPassword.equals(updatedUser.getPassword())) {
                user.setPassword(encryptDecrypt.encrypt(updatedUser.getNewPassword()));
            }
            else {
                throw new CustomErrorsException("incorrect password");
            }
        }
        if(updatedUser.getPaymentCards() != null && !updatedUser.getPaymentCards().isEmpty()) {
            Set<PaymentCard> newPaymentCards = updatedUser.getPaymentCards();
            Set<PaymentCard> paymentCards = user.getPaymentCards();
            Set<PaymentCard> updatedPaymentCards = new HashSet<PaymentCard>();
            for(PaymentCard newPaymentCard : newPaymentCards) {
                for(PaymentCard paymentCard : paymentCards) {
                    String oldCard_Number = encryptDecrypt.decrypt(paymentCard.getCard_Number());
                    String newCard_Number = newPaymentCard.getCard_Number();
                    if(newCard_Number.equals(oldCard_Number)) {
                        PaymentCard tempPaymentCard = paymentCard;
                        paymentCard.setExpiryDate(newPaymentCard.getExpiryDate());
                        if(paymentCard.getAddress() != null && newPaymentCard.getAddress() != null) {
                            BillingAddress billingAddress = paymentCard.getAddress();
                            billingAddress.setPaymentCard(paymentCard);
                            if(newPaymentCard.getAddress().getCity() != null) {
                                billingAddress.setCity(newPaymentCard.getAddress().getCity());
                            }
                            if(newPaymentCard.getAddress().getState() != null) {
                                billingAddress.setState(newPaymentCard.getAddress().getState());
                            }
                            if(newPaymentCard.getAddress().getStreet() != null) {
                                billingAddress.setStreet(newPaymentCard.getAddress().getStreet());
                            }
                            if(newPaymentCard.getAddress().getZipCode() != null) {
                                billingAddress.setZipCode(newPaymentCard.getAddress().getZipCode());
                            }
                            tempPaymentCard.setAddress(billingAddress);
                        }
                        else if(paymentCard.getAddress() == null && newPaymentCard.getAddress() != null) {
                            BillingAddress billingAddress = new BillingAddress();
                            billingAddress.setCity(newPaymentCard.getAddress().getCity());
                            billingAddress.setState(newPaymentCard.getAddress().getState());
                            billingAddress.setStreet(newPaymentCard.getAddress().getStreet());
                            billingAddress.setZipCode(newPaymentCard.getAddress().getZipCode());
                            billingAddress.setPaymentCard(paymentCard);
                            tempPaymentCard.setAddress(billingAddress);
                        }
                        tempPaymentCard.setUser(user);
                        updatedPaymentCards.add(tempPaymentCard);
                    }
                }
            }
            user.setPaymentCards(updatedPaymentCards);
        }
        if(user.getAddress() != null && updatedUser.getAddress() != null) {
            HomeAddress newAddress = updatedUser.getAddress();
            HomeAddress address = user.getAddress();
            if(newAddress.getCity() != null) {
                address.setCity(newAddress.getCity());
            }
            if(newAddress.getState() != null) {
                address.setState(newAddress.getState());
            }
            if(newAddress.getStreet() != null) {
                address.setStreet(newAddress.getStreet());
            }
            if(newAddress.getZipCode() != null) {
                address.setZipCode(newAddress.getZipCode());
            }
            address.setUser(user);
            user.setAddress(address);
        }
        else if(user.getAddress() == null && updatedUser.getAddress() != null) {
            HomeAddress newAddress = updatedUser.getAddress();
            newAddress.setUser(user);
            user.setAddress(newAddress);
        }
        if(updatedUser.isPromotionEnabled() != null && updatedUser.isPromotionEnabled() != user.isPromotionEnabled()) {
            user.setPromotionEnabled(updatedUser.getPromotionEnabled());
        }

        
        editedUser = userRepository.save(user);
        Set<PaymentCard> savedPaymentCards = editedUser.getPaymentCards();
        Set<PaymentCard> savedDecryptedPaymentCards = new HashSet<PaymentCard>();
        for(PaymentCard paymentCard : savedPaymentCards) {
            PaymentCard paymentCardDecrypted = new PaymentCard();
            paymentCardDecrypted.setAddress(paymentCard.getAddress());
            paymentCardDecrypted.setCard_Number(encryptDecrypt.decrypt(paymentCard.getCard_Number()));
            paymentCardDecrypted.setExpiryDate(paymentCard.getExpiryDate());
            paymentCardDecrypted.setPaymentID(paymentCard.getPaymentID());
            savedDecryptedPaymentCards.add(paymentCardDecrypted);
        }
        editedUser.setPaymentCards(savedDecryptedPaymentCards);
        return editedUser;
    }

    //@Transactional
    public User getUser(String userName) {
        User getUser = userRepository.findByUserName(userName);
        System.out.println(getUser.getPassword());
        Set<PaymentCard> savedPaymentCards = getUser.getPaymentCards();
        Set<PaymentCard> savedDecryptedPaymentCards = new HashSet<PaymentCard>();
        for(PaymentCard paymentCard : savedPaymentCards) {
            PaymentCard paymentCardDecrypted = new PaymentCard();
            paymentCardDecrypted.setAddress(paymentCard.getAddress());
            paymentCardDecrypted.setCard_Number(encryptDecrypt.decrypt(paymentCard.getCard_Number()));
            paymentCardDecrypted.setExpiryDate(paymentCard.getExpiryDate());
            paymentCardDecrypted.setPaymentID(paymentCard.getPaymentID());
            savedDecryptedPaymentCards.add(paymentCardDecrypted);
        }
        getUser.setPaymentCards(savedDecryptedPaymentCards);
        getUser.setPassword(encryptDecrypt.decrypt(getUser.getPassword()));
        return getUser;
    }
}
