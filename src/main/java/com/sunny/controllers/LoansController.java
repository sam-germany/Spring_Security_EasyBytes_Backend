package com.sunny.controllers;

import com.sunny.model.Customer;
import com.sunny.model.Loans;
import com.sunny.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestBody Customer customer) {

        List<Loans> loans  = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());

        if(loans != null) {
            return  loans;
        }else {
            return  null;
        }
    }
}
