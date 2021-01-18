package com.sunny.controllers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("/myBalance")
    public String getNotices(String input) {
        return "Here are the myBalance details from the DB";
    }
}
