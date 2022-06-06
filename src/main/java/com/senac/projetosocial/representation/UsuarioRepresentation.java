package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Imagem;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface UsuarioRepresentation {

    @Data
    @Getter
    @Setter
    class CriarOuAtualizar {

        @NotNull(message = "O campo nome não pode ser nulo")
        @Size(min = 1, max = 100, message = "O campo nome deve ter entre 1 a 100 caracteres")
        private String nome;

        @NotNull(message = "O campo email não pode ser nulo")
        @Size(min = 1, max = 150, message = "O campo email deve ter entre 1 a 150 caracteres")
        private String email;

        @NotNull(message = "O campo senha não pode ser nulo")
        @Size(min = 6, max = 100, message = "O campo senha deve ter entre 1 a 100 caracteres")
        private String senha;

        @NotNull(message = "O campo confirmação da senha não pode ser nulo")
        @Size(min = 1, max = 100, message = "O campo confirmação da senha deve ter entre 1 a 100 caracteres")
        private String confirmarSenha;

        @NotNull(message = "O perfil é obrigatório")
        private Long perfilPermissao;

        private Long imagem;

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detail {
        private Long id;
        private String nome;
        private String email;
        private String senha;
        private String confirmarSenha;
        private LocalDateTime dataHoraCadastro;
        private StatusEnum status;
        private PerfilPermissao perfilPermissao;
        private Imagem imagem;
        public static Detail from(Usuario usuario) {
            return Detail.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .senha(usuario.getSenha())
                    .confirmarSenha(usuario.getConfirmarSenha())
                    .dataHoraCadastro(usuario.getDataHoraCadastro())
                    .status(usuario.getStatus())
                    .perfilPermissao(usuario.getPerfilPermissao())
                    .imagem(usuario.getImagem())
                    .build();
        }

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Resumo {
        private String nome;
        private String email;
        private Imagem imagem;

        public static Resumo from(Usuario usuario) {
            return Resumo.builder()
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .imagem(usuario.getImagem())
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

    @Data
    @Getter
    @Builder
    class UsuarioToken{
        private Long id;
        private String nome;
        private String email;
        private PerfilPermissao perfilPermissao;

        public static UsuarioToken from(Usuario usuario){
            return UsuarioToken.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .perfilPermissao(usuario.getPerfilPermissao())
                    .build();
        }


    }

}
