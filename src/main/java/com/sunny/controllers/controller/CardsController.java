package com.sunny.controllers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/myCards")
    public String getNotices(String input) {
        return "Here are the myCards details from the DB";
    }
}
