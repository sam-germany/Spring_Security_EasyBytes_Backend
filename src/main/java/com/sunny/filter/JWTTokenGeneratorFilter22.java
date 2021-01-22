package com.sunny.filter;

import com.sunny.constants.SecurityConstants22;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//(video 77)
public class JWTTokenGeneratorFilter22 extends OncePerRequestFilter {

	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)  throws IOException, ServletException {

		Authentication authentication22 = SecurityContextHolder.getContext().getAuthentication();

		if (null != authentication22) {
			SecretKey key22 = Keys.hmacShaKeyFor(SecurityConstants22.JWT_KEY.getBytes(StandardCharsets.UTF_8));

			String jwt22 = Jwts.builder()
					         .setIssuer("Eazy Bank")
					         .setSubject("JWT Token")
						     .claim("username", authentication22.getName())
					         .claim("authorities", populateAuthorities22(authentication22.getAuthorities()))
					         .setIssuedAt(new Date())
					         .setExpiration(new Date((new Date()).getTime() + 300000000))
					         .signWith(key22)
			                 .compact();
			System.out.println(jwt22+"  ++++++++++++++");
			response.setHeader(SecurityConstants22.JWT_HEADER, jwt22);
		}

		chain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		return !request.getServletPath().equals("/user");
	}
	
	private String populateAuthorities22(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet22 = new HashSet<>();
        for (GrantedAuthority authority22 : collection) {
        	authoritiesSet22.add(authority22.getAuthority());
        }
        return String.join(",", authoritiesSet22);
	}
}
/*
return !request.getServletPath().equals("/user");    <-- as at the time of login we will generate a Token so in this application
                                                   he creates   /user   for login, so he create a little bit complicated logic
that if any other request comes it will not generate any token only when  /user  request comes then generate a jwt Token

----------------------------------------------------------------------------------------------
String.join(",", authoritiesSet22);     <-- here in the payload we are putting a (,) comma  and after that comma we are putting "authorities"










 */
