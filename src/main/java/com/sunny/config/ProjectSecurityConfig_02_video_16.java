package com.sunny.config;

import com.sunny.filter.AuthoritiesLoggingAfterFilter22;
import com.sunny.filter.AuthoritiesLoggingAtFilter22;
import com.sunny.filter.RequestValidationBeforeFilter22;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
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

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        .cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config22 = new CorsConfiguration();
                config22.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config22.setAllowedMethods(Collections.singletonList("*"));
                config22.setAllowCredentials(true);
                config22.setAllowedHeaders(Collections.singletonList("*"));
                config22.setExposedHeaders(Arrays.asList("Authorization"));
                config22.setMaxAge(3600L);
                return config22;
            }
        })
                .and()
                .csrf().disable()
               .addFilterBefore(new RequestValidationBeforeFilter22(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter22(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter22(), BasicAuthenticationFilter.class)
        //        .addFilterBefore(new JWTTokenValidatorFilter22(), BasicAuthenticationFilter.class)    // we need to validate Jwt the Token before Authentication so we use .addFilterBefore()
        //        .addFilterAfter(new JWTTokenGeneratorFilter22(), BasicAuthenticationFilter.class)      // after the Authentication only create Jwt Token  so we use .addFilterAfter()
        //        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/myAccount").hasRole("USER")
                .antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                .antMatchers("/myLoans").hasRole("ROOT")
                .antMatchers("/myCards").authenticated()
                .antMatchers("/user").authenticated()
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
http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     <-- (video 76)

by default a JSESSIONID is created, and with above command we are saying that do not create any http session
and Tokens, i will take care it myself, as we are using JWT token

SessionCreationPolicy.ALWAYS     <-- means that always generate a default token and session by spring
SessionCreationPolicy.IF_REQUIRED <-- generate token if it is required
SessionCreationPolicy.NEVER      <-- never generate a token but if a token is already their then use it
SessionCreationPolicy.STATELESS  <- it means never generate a token and if a token is already their then do not use it

--------------------------------------------------------------------------------------------------
.addFilterBefore(new RequestValidationBeforeFilter22(), BasicAuthenticationFilter.class)   <-- see (video 67) to understand this line

their is a predefined filter from spring  "BasicAuthenticationFilter" and we are putting our filter before it with .addFilterBefore()

--------------------------------------------------------------------------------------------------
.addFilterAfter(new AuthoritiesLoggingAfterFilter22(), BasicAuthenticationFilter.class)   <-- see (video 68) to understand .addFilterAfter()


====================================================================================================================
.csrf().disable()    <-- as we use JWT token so no need of .csrf() to handled as we are not using it (video 76)


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

--------------------------------------------------------------------------------------------------------------------------
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

config22.setExposedHeaders(Arrays.asList("Authorization22"));    <-- it means that we are generating our own header with the name key = "Authorization22"
---------------------------------------------------------------------------------

Note: another way of configuring CORS as  @Bean

    @Bean
        public CorsConfigurationSource  corsConfigurationSource() {

        	final CorsConfiguration   configuration = new CorsConfiguration();

        	configuration.setAllowedOrigins(Arrays.asList("*"));  // use  ("*")  if we want to allow calls from all the origins
        	configuration.setAllowedMethods(Arrays.asList("*"));  // use  ("*") to allow all methods calls e.g GET,POST,PUT,DELETE
        	configuration.setAllowCredentials(true);        // if we want to allow credentials to be included in the Http Response
        	                                                // Credentials means  cookies, Authorization Headers or SSL client certificates

        	configuration.setAllowedHeaders(Arrays.asList("*")); // as we have  Auth_Token  as Header so we can specific with specific name
          	                                                     // or ("*")  use this to allow all

        	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        	source.registerCorsConfiguration("/**", configuration);   // this is for the specific path like we have
        	                                                          //   /api/v1/users/login      this for the login
        	return source;                                              // so we can put his specific path or we can use  "*"  to allow all

        }




*/
}
