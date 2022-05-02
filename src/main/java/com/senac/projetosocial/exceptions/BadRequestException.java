package com.senac.projetosocial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
public class BadRequestException extends RuntimeException{
    public BadRequestException(){
        super();
    }
    public BadRequestException(final String message){
        super(message);
    }
    public BadRequestException(final String message, final Throwable cause){
        super(message, cause);
    }
    public BadRequestException(final Throwable cause) {
        super(cause);
    }
}
