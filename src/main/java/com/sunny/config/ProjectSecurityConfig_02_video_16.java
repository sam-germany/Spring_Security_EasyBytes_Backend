package com.sunny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

//video 12
@Configuration
public class ProjectSecurityConfig_02_video_16 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config22 = new CorsConfiguration();
                config22.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:5555"));
                config22.setAllowedMethods(Collections.singletonList("*"));
                config22.setAllowCredentials(true);
                config22.setAllowedHeaders(Collections.singletonList("*"));
                config22.setMaxAge(3600L);
                return config22;
            }
        })
                .and()
                .authorizeRequests()
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /*
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
    */


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
--------------------------------------------------------------------------
 */
  /*
 CQRS  configuration
 ------------------
 Collections.singletonList()   <-- here singletonList() means returns a List , this list is immutable ,we can not change it

config22.setAllowedOrigins()   <-- this application accepts only calls from the ports defined in this methods only
config22.setAllowedMethods()   <--  which methods are allowed  GET POST PATCH DELETE PUT  or   user (*) if we allow all
config22.setAllowCredentials()  <-- it means this application accepts  Credentials also
config22.setAllowedHeaders()   <-- it means all kind of Headers are allowed
config22.setMaxAge(3600L)      <-- it means Browser is allowed to save these CORS configuration for 3600 sec and
                                   after that Browser has to again make a new call to check if the CORS configuration has
                                   changed or not, it normally means check about the configuration after every 3600sec
config22.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:5555"));   <-- use Arrays.asList() for multiple objects

   */
}
