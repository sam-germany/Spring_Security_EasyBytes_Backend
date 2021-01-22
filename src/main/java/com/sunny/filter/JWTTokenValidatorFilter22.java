package com.sunny.filter;

import com.sunny.constants.SecurityConstants22;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
// video 77
public class JWTTokenValidatorFilter22 extends OncePerRequestFilter {

	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		String jwt22 = request.getHeader(SecurityConstants22.JWT_HEADER);

		if (null != jwt22) {
			try {
				SecretKey key22 = Keys.hmacShaKeyFor(SecurityConstants22.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				
				Claims claims22 = Jwts.parserBuilder()
	            					  .setSigningKey(key22)
						              .build()
						              .parseClaimsJws(jwt22)
						              .getBody();

				String username = String.valueOf(claims22.get("username"));
				String authorities = (String) claims22.get("authorities");

	Authentication auth22 = new UsernamePasswordAuthenticationToken(username,null,
	                                           					AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

				System.out.println(auth22 +"-----------------");

				SecurityContextHolder.getContext().setAuthentication(auth22);
			}catch (Exception e) {
				throw new BadCredentialsException("Invalid Token received!");
			}
			
		}
		chain.doFilter(request, response);
	}

	
	  @Override protected boolean shouldNotFilter(HttpServletRequest request) {
	  return request.getServletPath().equals("/user"); }
}
/*
.parseClaimsJws(jwt22)    <-- this is the method where all the checking works, here we are checking the coming Jwt with our key,
                              if the same key is used at the time of generating then we will get a response, if we use another key
                              at the time of generating and another key at the time of decoding then we will get a exception,

-------------------------------------------------------------------------------------------
claims22.get("username") <-- here we are getting the retrieving the data from payload with the "key" name

---------------------------------------------------------------------------------------------
Authentication auth = new UsernamePasswordAuthenticationToken(username,null, ......)

here we put (username, null, .... )    we put "null"  for the credentials as after we validate the Jwt token we do not need the
                                       password anymore in the application, so only "username" will put transferred forward  to application

---------------------------------------------------------------------------------------------
SecurityContextHolder.getContext().setAuthentication(auth22);    <-- image name   07_video_57   their is clearly show us the "SecurityContext"
                                                             after validation we are creating a new Authentication object by using
                                                             "new UsernamePasswordAuthenticationToken"   with "credentials=null"  and after
                                                             that we are replacing the Authentication object
                                                             that was created at the time of login or at the time of last request with this new
                                                             "new UsernamePasswordAuthenticationToken"  by  ".setAuthentication(auth22)"









 */
