package com.sunny.filter;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
// (video 67)
public class RequestValidationBeforeFilter22 implements Filter {

    public static final String AUTHENTICATION_SCHEMA_BASIC = "Basic";
    private Charset credentialsCharset = StandardCharsets.UTF_8;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String header22 = req.getHeader(AUTHORIZATION);

        if(header22 !=null) {
            if(StringUtils.startsWithIgnoreCase(header22, AUTHENTICATION_SCHEMA_BASIC)) {
                byte[] base64Token22 = header22.substring(6).getBytes(StandardCharsets.UTF_8);
                byte [] decoded22;
                try{
                    decoded22 = Base64.getDecoder().decode(base64Token22);
                    String token22 = new String(decoded22, getCredentialsCharset(req));
                    int delim = token22.indexOf(":");
                    if (delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token22.substring(0, delim);

                    if(email.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }catch (IllegalArgumentException e) {
                    throw  new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        chain.doFilter(request, response);
    }

    protected Charset getCredentialsCharset(HttpServletRequest request) {
        return getCredentialsCharset();
    }
    public Charset getCredentialsCharset() {
        return  this.credentialsCharset;
    }
}
/*
req.getHeader(AUTHORIZATION);    <-- here  AUTHORIZATION = "Authorization"  it only means  "Authorization
----------------------------
StringUtils.startsWithIgnoreCase(header22, AUTHENTICATION_SCHEMA_BASIC)  <-- here  AUTHENTICATION_SCHEMA_BASIC ="Basic"
-----------------------------------------------------------------------
here we are checking that the Token which we are getting starts with  "Basic" or not

StandardCharsets.UTF_8
-----------------------
Which character encoding should I use for my content, and how do I apply it to my content?

Content is composed of a sequence of characters. Characters represent letters of the alphabet, punctuation, etc.But
content is stored in a computer as a sequence of bytes, which are numeric values. Sometimes more than one byte is used
to represent a single character. Like codes used in espionage, the way that the sequence of bytes is converted to
characters depends on what key was used to encode the text. In this context, that key is called a character encoding.

indexOf(":");   <-  if String does not include ":" this character then return -1, otherwise return the index of the value

 */
