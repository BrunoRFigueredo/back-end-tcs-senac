package com.senac.projetosocial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
@ResponseBody
public class JWTAuthenticationException extends AuthenticationException{
    public JWTAuthenticationException(String errorMessage){
        super(errorMessage);
    }
}
