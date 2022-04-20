package com.senac.projetosocial.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
// classe para tratamento de exceptions
public class ErrorPayload {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}