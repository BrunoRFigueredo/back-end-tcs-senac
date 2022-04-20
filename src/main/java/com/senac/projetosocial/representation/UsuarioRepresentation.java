package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface UsuarioRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdate {

        @NotNull(message = "O campo nome não pode ser nulo")
        @Size(min = 1, max = 100, message = "O campo nome deve ter entre 1 a 100 caracteres")
        private String nome;

        @NotNull(message = "O campo email não pode ser nulo")
        @Size(min = 1, max = 150, message = "O campo email deve ter entre 1 a 150 caracteres")
        private String email;

        @NotNull(message = "O campo senha não pode ser nulo")
        @Size(min = 1, max = 100, message = "O campo senha deve ter entre 1 a 100 caracteres")
        private String password;

        @NotNull(message = "O campo confirmação da senha não pode ser nulo")
        @Size(min = 1, max = 100, message = "O campo confirmação da senha deve ter entre 1 a 100 caracteres")
        private String confirmPassword;

        @NotNull(message = "O perfil é obrigatório")
        private Long perfilPermissao;

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detail {
        private Long id;
        private String nome;
        private String email;
        private String password;
        private String confirmPassword;
        private LocalDateTime dataHoraCadastro;
        private StatusEnum status;
        private PerfilPermissao perfilPermissao;

        public static Detail from(Usuario usuario) {
            return Detail.builder()
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .confirmPassword(usuario.getConfirmPassword())
                    .dataHoraCadastro(usuario.getDataHoraCadastro())
                    .status(usuario.getStatus())
                    .perfilPermissao(usuario.getPerfilPermissao())
                    .build();
        }

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String nome;

        private static Lista from(Usuario usuario) {
            return Lista.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .build();
        }

        public static List<Lista> from(List<Usuario> usuarios) {
            return usuarios.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }

}
