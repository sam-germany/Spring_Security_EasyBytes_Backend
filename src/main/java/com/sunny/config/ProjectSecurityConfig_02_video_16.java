package com.sunny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
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
                config22.setAllowedOrigins(Collections.singletonList("*"));
                config22.setAllowedMethods(Collections.singletonList("*"));
                config22.setAllowCredentials(true);
                config22.setAllowedHeaders(Collections.singletonList("*"));
                config22.setMaxAge(3600L);
                return config22;
            }
        })
                .and()
                .csrf()
                .ignoringAntMatchers("/contact")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
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
 CQRS  configuration    (video 45-52)
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

-------------------------------------------------------------------------------------------------
CSRF  ->  Cross site Request Forgery   (video 51)
-----
as we handled as shown in this video the CSRF, the main point to understand only when we create a call from broweser
to a secured application api, as in our case is  "/myAccount"  then as we deifned above "CookieCsrfTokenRepository.withHttpOnlyFalse()"
then spring will generate a tocken and send with the cookies to the browser, next time when the call comes from
browser to the api this tocken will automatically  come with the request.

(video 54)  <-- .csrf().ignoringAntMatchers("/contact")     but if we define any  api url inside it, then when
broweser will call this url then no   CSRF token will be generated for this url

----------------------------------------------------------------------------------------------
CookieCsrfTokenRepository.withHttpOnlyFalse()      (video 53)
-----------------------------------------------
CSRF protection with Spring CookieCsrfTokenRepository works as follows:

    The client makes a GET request to Server (Spring Boot Backend), e.g. request for the main page
    Spring sends the response for GET request along with Set-cookie header which contains securely generated XSRF Token
    The browser sets the cookie with XSRF Token
    While sending a state-changing request (e.g. POST) the client (might be angular) copies the cookie value to the HTTP request header
    The request is sent with both header and cookie (browser attaches the cookie automatically)
    Spring compares the header and the cookie values, if they are the same the request is accepted, otherwise, 403 is
     returned to the client

The method withHttpOnlyFalse allows angular to read XSRF cookie. Make sure that Angular makes XHR request with withCreddentials
 flag set to true.

--------------------------------------------------------------------------------------------------
.csrf().ignoringAntMatchers("/contact")   (video 54)

with this   .ignoringAntMatchers() we have defined that

   */
}
