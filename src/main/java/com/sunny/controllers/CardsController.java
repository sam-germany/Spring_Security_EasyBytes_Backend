package com.sunny.controllers;

import com.sunny.model.Cards;
import com.sunny.model.Customer;
import com.sunny.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
     CardsRepository cardsRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {

        List<Cards> cards = cardsRepository.findByCustomerId(customer.getId());

        if(cards != null) {
            return  cards;
        }else {
            return  null;
        }
    }


}
