package com.sunny.config;

import com.sunny.model.Customer;
import com.sunny.model.SecurityCustomer;
import com.sunny.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EasyBankDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customerList = customerRepository.findByEmail(username);

 //       System.out.println("-------------222");
        if( customerList.size() == 0) {
            throw new UsernameNotFoundException("User details not found for the user: "+ username);
        }
        return new SecurityCustomer(customerList.get(0));
    }
}
