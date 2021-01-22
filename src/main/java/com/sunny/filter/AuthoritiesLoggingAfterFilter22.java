package com.sunny.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAfterFilter22 implements Filter {

    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAfterFilter22.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(null != authentication) {
            LOG.info("User "+ authentication.getName() + " is successfully authenticated and "
                                                     + "has the authorities " + authentication.getAuthorities().toString());
        }
        chain.doFilter(request, response);
    }
}
/*
SecurityContextHolder.getContext().getAuthentication();  <- to understand this see the image 07_video_57   a Authentication object is
created in the SecurityContextHolder with the username and Authorities, the password is removed by the Spring after successful login
 */
