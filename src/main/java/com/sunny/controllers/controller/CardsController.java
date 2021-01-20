package com.sunny.controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @Autowired
    com.eazybytes.repository.CardsRepository cardsRepository;

    @GetMapping("/myCards")
    public String getNotices(String input)
    {
        return "Here are the myCards details from the DB";
    }
}
