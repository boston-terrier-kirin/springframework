package com.example.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			// 仮にここでchainしてしまっても、後工程で403になる。
			// ⇒Sessionが有効になっていると、ログイン済みと判定されて200になる。
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken token = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(token);
		
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

		Key key = Keys.hmacShaKeyFor(SecurityConstants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

		/**
		 * Tokenの有効期限が切れているとここでエラーになる。
		 */
		String user = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(token)
							.getBody()
							.getSubject();

		logger.info("★getAuthentication");
		logger.info(user);

		if (user != null) {
			return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
		}

		return null;
	}
}
