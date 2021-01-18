package com.sunny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

//video 12
@Configuration
public class ProjectSecurityConfig_02_video_16 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/myAccount").authenticated()
            .antMatchers("/myBalance").authenticated()
            .antMatchers("/myLoans").authenticated()
            .antMatchers("/myCards").authenticated()
            .antMatchers("/notices").permitAll()
            .antMatchers("/contact").permitAll()
            .and()
            .formLogin().and()
            .httpBasic();
    }


    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception{

        InMemoryUserDetailsManager userDetailsService22 = new InMemoryUserDetailsManager();
        UserDetails user1 = User.withUsername("admin").password("12345").authorities("admin").build();
        UserDetails user2 = User.withUsername("user").password("12345").authorities("read").build();

        userDetailsService22.createUser(user1);
        userDetailsService22.createUser(user2);

        auth.userDetailsService(userDetailsService22);
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return  new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }



//    ------------------------------------------------
/*
    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.inMemoryAuthentication()
            .withUser("admin").password("12345").authorities("admin")
            .and()
            .withUser("user").password("12345").authorities("read")
            .and()
            .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
 */
}
