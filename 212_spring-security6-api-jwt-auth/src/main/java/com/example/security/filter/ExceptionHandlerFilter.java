package com.example.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.exception.EntityNotFoundException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("★ExceptionHandlerFilter.doFilterInternal");
        System.out.println(request.getRequestURI());

        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            // ユーザが見つからない場合は、UserServiceImplがEntityNotFoundExceptionを投げる。
            System.out.println("User Not Found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            response.getWriter().flush();
        } catch (JWTVerificationException e) {
            System.out.println("INVALID JWT");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            response.getWriter().flush();
        } catch (RuntimeException e) {
            System.out.println(e.toString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("BAD REQUEST");
            response.getWriter().flush();
        }
    }

}
