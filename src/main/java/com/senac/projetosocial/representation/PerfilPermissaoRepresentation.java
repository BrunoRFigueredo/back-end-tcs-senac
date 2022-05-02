package com.senac.projetosocial.representation;

import com.senac.projetosocial.model.PerfilPermissao;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Permission;
import java.util.List;
import java.util.stream.Collectors;

public interface PerfilPermissaoRepresentation {

    @Data
    @Getter
    @Setter
    class CriarOuAtualizar {

        @NotNull(message = "O campo perfil não pode ser nulo")
        @Size(min = 1, max = 50, message = "O campo perfil deve ter entre 1 e 50 caracteres")
        private String perfil;

        @NotNull(message = "O campo permissao não pode ser nulo")
        private String[] permissao;

    }

    @Data
    @Getter
    @Builder
    class Detalhes{
        private Long id;
        private String perfil;
        private String[] permissao;

        public static Detalhes from(PerfilPermissao perfilPermissao){
            return Detalhes.builder()
                    .id(perfilPermissao.getId())
                    .perfil(perfilPermissao.getPerfil())
                    .permissao(perfilPermissao.getPermissao())
                    .build();
        }
    }
    @Data
    @Getter
    @Builder
    class ListaPermissao{
        private Long id;
        private String perfil;

        private static ListaPermissao from(PerfilPermissao perfilPermissao){
            return ListaPermissao.builder()
                    .id(perfilPermissao.getId())
                    .perfil(perfilPermissao.getPerfil())
                    .build();
        }

        public static List<ListaPermissao> from(List<PerfilPermissao> perfilPermissao){
            return perfilPermissao
                    .stream()
                    .map(ListaPermissao::from)
                    .collect(Collectors.toList());
        }
    }
}
