package com.senac.projetosocial.representation;

import com.senac.projetosocial.model.PerfilPermissao;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public interface LoginRepresentation {
    @Data
    @Builder
    class LoginResponse{
        private String token;
        private String tokenType;
        private UsuarioRepresentation.UsuarioToken usuario;
        private String roles;
    }

    @Data
    class Login{
        private String email;
        private String password;
    }
}
