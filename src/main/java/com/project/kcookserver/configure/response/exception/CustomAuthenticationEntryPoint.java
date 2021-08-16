package com.project.kcookserver.configure.response.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))+" : Exception transmitted to AuthenticationEntryPoint");

        String exception = (String)request.getAttribute("exception");
        if (exception.equals(CustomExceptionStatus.NOT_AUTHENTICATED_ACCOUNT.getMessage())) {
            response.sendRedirect("/errors/not-authenticated-account");
        }
        else if (exception.equals(CustomExceptionStatus.INVALID_JWT.getMessage())) {
            response.sendRedirect("/errors/invalid-jwt");
        }
        else if (exception.equals(CustomExceptionStatus.EMPTY_JWT.getMessage())) {
            response.sendRedirect("/errors/empty-jwt");
        }
    }
}
