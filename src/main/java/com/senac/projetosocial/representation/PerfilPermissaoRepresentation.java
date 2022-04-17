package com.senac.projetosocial.representation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface PerfilPermissaoRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdate {

        @NotNull(message = "O campo perfil não pode ser nulo")
        @Size(min = 1, max = 50, message = "O campo perfil deve ter entre 1 e 50 caracteres")
        private String perfil;

        @NotNull(message = "O campo permissao não pode ser nulo")
        private String[] permissao;

    }

}
