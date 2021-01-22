package com.sunny.config;

import com.sunny.model.Authority;
import com.sunny.model.Customer;
import com.sunny.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String username = authentication.getName();
      String pwd = authentication.getCredentials().toString();
        List<Customer> customer1 = customerRepository.findByEmail(username);

        if(customer1.size() > 0) {
     //       System.out.println("----------111");
            if(passwordEncoder.matches(pwd, customer1.get(0).getPwd())) {
                return  new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities22(customer1.get(0).getAuthorities()));
            }else {
                throw  new BadCredentialsException("Invalid Password");
            }
        }else {
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities22(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority x : authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(x.getName()));
        }
        return grantedAuthorities;
    }




    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
