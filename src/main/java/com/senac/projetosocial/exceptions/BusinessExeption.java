package com.senac.projetosocial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessExeption extends RuntimeException {

    public BusinessExeption() {
        super();
    }

    public BusinessExeption(final String message) {
        super(message);
    }

    public BusinessExeption(final String message, final Throwable cause) {
        super(message, cause);
    }

}
