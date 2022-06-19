package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.Usuario;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface InstituicaoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{
        @NotNull(message = "O nome da instituição não pode ser nulo!")
        @Size(min = 1, max = 80, message = "O nome da instituição deve conter entre 1 e 80 caracteres")
        private String nome;

        @NotNull(message = "A descrição da instituição não pode ser nula!")
        @Size(min = 1, max = 255, message = "A descrição da instituição deve conter entre 1 e 255 caracteres")
        private String descricao;

        @NotNull(message = "O cnpj da instituição não pode ser nulo!")
        @Size(min = 1, max = 45, message = "O cnpj da instituição deve conter entre 1 e 45 caracteres")
        private String cnpj;

        @NotNull(message = "A conta da instituição não pode ser nula!")
        @NotEmpty(message = "A conta da instituição não pode ser vazia!")
        private String conta;

        @NotNull(message = "A agência da instituição não pode ser nula!")
        @NotEmpty(message = "A agência da instituição não pode ser vazia!")
        private String agencia;

        @Size(min = 1, max = 100, message = "O email da instituição deve conter entre 1 e 100 caracteres")
        private String email;

        @NotNull(message = "O telefone da instituição não pode ser nulo")
        private String telefone;

        @Size(min = 1, max = 20, message = "O pais da instituição deve conter entre 1 e 20 caracteres")
        @NotNull(message = "O pais da instituição não pode ser nulo!")
        private String pais;

        @Size(min = 1, max = 2, message = "O estado da instituição deve conter 1 ou 2 caracteres")
        @NotNull(message = "O estado da instituição não pode ser nulo!")
        private String estado;

        @Size(min = 1, max = 50, message = "A cidade da instituição deve conter entre 1 e 50 caracteres")
        @NotNull(message = "A cidade da instituição não pode ser nula!")
        private String cidade;

        @Size(min = 1, max = 100, message = "O bairro da instituição deve conter entre 1 e 100 caracteres")
        @NotNull(message = "O bairro da instituição não pode ser nulo!")
        private String bairro;

        @Size(min = 1, max = 120, message = "O logradouro da instituição deve conter entre 1 e 120 caracteres")
        @NotNull(message = "O logradouro da instituição não pode ser nulo!")
        private String logradouro;

        @NotNull(message = "O numero do endereço da instituição não pode ser nulo!")
        private int numero;

        @NotNull(message = "O cep da instituição não pode ser nulo!")
        private String cep;

        private StatusEnum status;

        private Long usuario;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe{
        private Long id;
        private String nome;
        private String descricao;
        private String cnpj;
        private String conta;
        private String agencia;
        private String pix;
        private String email;
        private String telefone;
        private String pais;
        private String estado;
        private String cidade;
        private String bairro;
        private String logradouro;
        private int numero;
        private String cep;
        private StatusEnum status;
        private UsuarioRepresentation.Resumo usuario;

        public static Detalhe from(Instituicao instituicao){
            return Detalhe.builder()
                    .id(instituicao.getId())
                    .nome(instituicao.getNome())
                    .descricao((instituicao.getDescricao()))
                    .conta(instituicao.getConta())
                    .agencia(instituicao.getAgencia())
                    .cnpj(instituicao.getCnpj())
                    .email(instituicao.getEmail())
                    .telefone(instituicao.getTelefone())
                    .pais(instituicao.getPais())
                    .estado(instituicao.getEstado())
                    .cidade(instituicao.getCidade())
                    .bairro(instituicao.getBairro())
                    .logradouro(instituicao.getLogradouro())
                    .numero(instituicao.getNumero())
                    .cep(instituicao.getCep())
                    .status(instituicao.getStatus())
                    .usuario(UsuarioRepresentation.Resumo.from(instituicao.getUsuario()))
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Resumo{
        private Long id;
        private String nome;
        private String descricao;
        private String cnpj;
        private String telefone;

        public static Resumo from(Instituicao instituicao){
            return Resumo.builder()
                    .id(instituicao.getId())
                    .nome(instituicao.getNome())
                    .descricao((instituicao.getDescricao()))
                    .cnpj(instituicao.getCnpj())
                    .telefone(instituicao.getTelefone())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista{
        private Long id;
        private String nome;
        private String cnpj;
        private String pix;
        private String email;
        private String telefone;
        private Usuario usuario;

        private static Lista from (Instituicao instituicao){
            return  Lista.builder()
                    .id(instituicao.getId())
                    .nome(instituicao.getNome())
                    .cnpj(instituicao.getCnpj())
                    .email(instituicao.getEmail())
                    .telefone(instituicao.getTelefone())
                    .usuario(instituicao.getUsuario())
                    .build();
        }
        public static List<Lista> from (List<Instituicao> instituicoes){
            return instituicoes
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }

}
