package com.sunny.controllers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/myLoans")
    public String getNotices(String input) {
        return "Here are the myLoans details from the DB";
    }
}
