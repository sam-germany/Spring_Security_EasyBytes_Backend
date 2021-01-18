package com.sunny.controllers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/contact")
    public String getNotices(String input) {
        return "Here are the contact details from the DB";
    }
}
