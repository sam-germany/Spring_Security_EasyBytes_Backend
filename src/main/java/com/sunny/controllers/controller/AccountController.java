package com.sunny.controllers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/myAccount")
    public String getNotices(String input) {
        return "Here are the myAccount details from the DB";
    }
}
