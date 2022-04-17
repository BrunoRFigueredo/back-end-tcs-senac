package com.senac.projetosocial.exeptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorPayload {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
