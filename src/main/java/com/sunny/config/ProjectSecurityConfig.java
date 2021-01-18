package com.sunny.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//video 12
//@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

 /*       http.authorizeRequests()
            .antMatchers("/myAccount").authenticated()
            .antMatchers("/myBalance").authenticated()
            .antMatchers("/myLoans").authenticated()
            .antMatchers("/myCards").authenticated()
            .antMatchers("/notices").permitAll()
            .antMatchers("/contact").permitAll()
            .and()
            .formLogin().and()
            .httpBasic();
 */

// to denyAll() requests --------------------------------------------
/*        http.authorizeRequests().anyRequest().denyAll().and()
                .formLogin().and().httpBasic();
*/

// to permitAll()  requests --------------------------------------------
/*        http.authorizeRequests().anyRequest().permitAll().and()
                .formLogin().and().httpBasic();
*/








    }
}
