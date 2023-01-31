package com.example.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.security.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("★JWTAuthorizationFilter.doFilterInternal");

        String header = request.getHeader(SecurityConstants.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            System.out.println("★JWTAuthorizationFilter.トークンなし");

            /**
             * SecurityConfigで要認証にしているURLにアクセスすると、SecurityContextHolderにauthがないので、SpringSecurityがエラーにする。
             * /authenticateはpermitAllにしているので問題なし。それ以外はトークンはエラーになる。
             */

            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, "");
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                            .build()
                            .verify(token)
                            .getSubject();

        // 権限を持たせていないからといって、new UsernamePasswordAuthenticationToken(user, null)を使うのはだめ。
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());

        // これをやると、Springに認可済みを伝えることができる。
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}

// 権限を持たせていないからといって、new UsernamePasswordAuthenticationToken(user, null)を使うのはだめ。
/*
    public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false); // ★ここ
    }

    public UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // ★ここ
    }
 */