package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
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
import java.util.List;
import java.util.stream.Collectors;

public interface VoluntarioRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{
        @NotNull(message = "O nome do voluntario não pode ser nulo!")
        @Size(min = 1, max = 200, message = "O nome do voluntario deve conter entre 1 e 200 caracteres")
        private String nome;

        @NotNull(message = "A biografia do voluntario não pode ser nula!")
        @Size(min = 1, max = 255, message = "A biografia do voluntario deve conter entre 1 e 255 caracteres")
        private String biografia;

        @NotNull(message = "O cpf da instituição não pode ser nulo!")
        @Size(min = 1, max = 11, message = "O cpf do voluntario deve conter entre 1 e 11 caracteres")
        private String cpf;

        private String whatsapp;

        @Size(min = 1, max = 200, message = "O email do voluntario deve conter entre 1 e 100 caracteres")
        private String email;

        @Column(name = "celular")
        @NotNull(message = "O celular do voluntario nao pode ser nulo!")
        private Long celular;

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
        private String nome;
        private String biografia;
        private String cpf;
        private String whatsapp;
        private String email;
        private Long celular;
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
                    .nome(voluntario.getNome())
                    .biografia(voluntario.getBiografia())
                    .cpf(voluntario.getCpf())
                    .whatsapp(voluntario.getWhatsapp())
                    .email(voluntario.getEmail())
                    .celular(voluntario.getCelular())
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
    class Lista{
        private Long id;
        private String nome;
        private Long celular;
        private String whatsapp;
        private String email;
        private String bairro;
        private String cidade;

        private static Lista from (Voluntario voluntario){
            return Lista.builder()
                    .id(voluntario.getId())
                    .nome(voluntario.getNome())
                    .celular(voluntario.getCelular())
                    .whatsapp(voluntario.getWhatsapp())
                    .email(voluntario.getEmail())
                    .bairro(voluntario.getBairro())
                    .cidade(voluntario.getCidade())
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
