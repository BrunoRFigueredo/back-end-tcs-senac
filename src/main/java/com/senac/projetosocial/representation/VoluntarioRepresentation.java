package com.senac.projetosocial.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senac.projetosocial.enums.SexoEnum;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.model.Voluntario;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface VoluntarioRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{
        @NotNull(message = "A biografia do voluntario não pode ser nula!")
        @Size(min = 1, max = 255, message = "A biografia do voluntario deve conter entre 1 e 255 caracteres")
        private String biografia;

        @NotNull(message = "O cpf do voluntario não pode ser nulo!")
        @Size(min = 1, max = 11, message = "O cpf do voluntario deve conter entre 1 e 11 caracteres")
        private String cpf;

        @NotNull(message = "A data de nascimento do voluntario não pode ser nulo!")
        private LocalDate dataNascimento;

        @NotNull(message = "O sexo do voluntário não pode ser nulo")
        private SexoEnum sexo;

        @NotNull(message = "O telefone do voluntario não pode ser nulo!")
        private String telefone;

        @Size(min = 1, max = 200, message = "O email do voluntario deve conter entre 1 e 100 caracteres")
        private String email;

        @Size(min = 1, max = 20, message = "O pais do voluntario deve conter entre 1 e 20 caracteres")
        @NotNull(message = "O pais da instituição não pode ser nulo!")
        private String pais;

        @Size(min = 1, max = 2, message = "O estado do voluntario deve conter 1 ou 2 caracteres")
        @NotNull(message = "O estado da instituição não pode ser nulo!")
        private String estado;

        @Size(min = 1, max = 50, message = "A cidade do voluntario deve conter entre 1 e 50 caracteres")
        @NotNull(message = "A cidade da instituição não pode ser nula!")
        private String cidade;

        @Size(min = 1, max = 80, message = "O bairro do voluntário deve conter entre 1 e 80 caracteres")
        @NotNull(message = "O bairro do voluntário nao pode ser nulo")
        private String bairro;

        @Size(min = 1, max = 120, message = "O logradouro do voluntario deve conter entre 1 e 120 caracteres")
        @NotNull(message = "O logradouro da instituição não pode ser nulo!")
        private String logradouro;

        @NotNull(message = "O numero do endereço do voluntario não pode ser nulo!")
        private Long numero;

        @NotNull(message = "O cep do voluntario não pode ser nulo!")
        private Long cep;

        private Long usuario;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe{
        private String biografia;
        private String cpf;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataNascimento;
        private String telefone;
        private String email;
        private String pais;
        private String estado;
        private String cidade;
        private String bairro;
        private String logradouro;
        private Long numero;
        private Long cep;
        private StatusEnum status;
        private UsuarioRepresentation.Resumo usuario;

        public static Detalhe from (Voluntario voluntario){
            return Detalhe.builder()
                    .biografia(voluntario.getBiografia())
                    .cpf(voluntario.getCpf())
                    .dataNascimento(voluntario.getDataNascimento())
                    .telefone(voluntario.getTelefone())
                    .email(voluntario.getUsuario().getEmail())
                    .pais(voluntario.getPais())
                    .estado(voluntario.getEstado())
                    .bairro(voluntario.getBairro())
                    .cidade(voluntario.getCidade())
                    .logradouro(voluntario.getLogradouro())
                    .numero(voluntario.getNumero())
                    .cep(voluntario.getCep())
                    .status(voluntario.getStatus())
                    .usuario(UsuarioRepresentation.Resumo.from(voluntario.getUsuario()))
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
        private String email;
        private String telefone;

        public static Resumo from(Voluntario voluntario){
            return Resumo.builder()
                    .id(voluntario.getId())
                    .email((voluntario.getUsuario().getEmail()))
                    .telefone(voluntario.getTelefone())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista{
        private Long id;
        private String telefone;
        private String email;
        private String bairro;
        private String cidade;
        private SexoEnum sexo;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataNascimento;
        private UsuarioRepresentation.Resumo usuario;
        private static Lista from (Voluntario voluntario){
            return Lista.builder()
                    .id(voluntario.getId())
                    .telefone(voluntario.getTelefone())
                    .email(voluntario.getUsuario().getEmail())
                    .bairro(voluntario.getBairro())
                    .cidade(voluntario.getCidade())
                    .dataNascimento(voluntario.getDataNascimento())
                    .sexo(voluntario.getSexo())
                    .usuario(UsuarioRepresentation.Resumo.from(voluntario.getUsuario()))
                    .build();
        }

        public static List<Lista> from(List<Voluntario> voluntarios){
            return voluntarios
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
