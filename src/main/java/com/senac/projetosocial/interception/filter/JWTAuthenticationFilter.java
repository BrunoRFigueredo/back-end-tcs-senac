package com.senac.projetosocial.interception.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senac.projetosocial.authorization.TokenStructure;
import com.senac.projetosocial.exceptions.ErrorPayload;
import com.senac.projetosocial.exceptions.JWTAuthenticationException;
import com.senac.projetosocial.exceptions.RestExceptionHandler;
import com.senac.projetosocial.jwt.TokenValidation;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final RestExceptionHandler restExceptionHandler;
    private final ObjectMapper objectMapper;
    private final TokenValidation tokenValidation;

    private Boolean checkAllowedPatchs(HttpServletRequest request){
        return (request.getRequestURI().contains("/perfil-permissao/") ||
                request.getRequestURI().contains("") ||
                request.getRequestURI().contains("/login"));
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!checkAllowedPatchs(request)) {
            try {
                String HEADER_STRING = "Authorization";
                String bearerToken = Optional.ofNullable(request.getHeader(HEADER_STRING)).orElse(Strings.EMPTY);
                TokenStructure tokenStructure = tokenValidation.buscarTokenData(bearerToken);
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(tokenStructure, null,
                                        Collections.emptyList()));
                filterChain.doFilter(request, response);
            } catch (JWTAuthenticationException e) {
                ErrorPayload errorPayload = restExceptionHandler.handleUnauthorized(e, request);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(objectMapper.writeValueAsString(errorPayload));
            }
        } else {
                filterChain.doFilter(request, response);
            }
        }
    }
