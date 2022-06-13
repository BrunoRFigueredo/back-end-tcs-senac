package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "instituicao")
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_instituicao")
    @SequenceGenerator(name = "sequence_instituicao", sequenceName = "sequence_instituicao", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    @NotNull(message = "O nome da instituição não pode ser nulo!")
    @Size(min = 1, max = 80, message = "O nome da instituição deve conter entre 1 e 80 caracteres")
    private String nome;

    @Column(name = "descricao")
    @NotNull(message = "A descrição da instituição não pode ser nula!")
    @Size(min = 1, max = 255, message = "A descrição da instituição deve conter entre 1 e 255 caracteres")
    private String descricao;

    @Column(name = "cnpj")
    @NotNull(message = "O cnpj da instituição não pode ser nulo!")
    @Size(min = 1, max = 45, message = "O cnpj da instituição deve conter entre 1 e 45 caracteres")
    private String cnpj;

    @Column(name = "conta")
    @NotNull(message = "A conta da instituição não pode ser nula!")
    @NotEmpty(message = "A conta da instituição não pode ser vazia!")
    private String conta;

    @Column(name = "agencia")
    @NotNull(message = "A agência da instituição não pode ser nula!")
    @NotEmpty(message = "A agência da instituição não pode ser vazia!")
    private String agencia;


    @Column(name = "email")
    @Size(min = 1, max = 100, message = "O email da instituição deve conter entre 1 e 100 caracteres")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "pais")
    @Size(min = 1, max = 20, message = "O pais da instituição deve conter entre 1 e 20 caracteres")
    @NotNull(message = "O pais da instituição não pode ser nulo!")
    private String pais;

    @Column(name = "estado")
    @Size(min = 1, max = 2, message = "O estado da instituição deve conter 1 ou 2 caracteres")
    @NotNull(message = "O estado da instituição não pode ser nulo!")
    private String estado;

    @Column(name = "cidade")
    @Size(min = 1, max = 50, message = "A cidade da instituição deve conter entre 1 e 50 caracteres")
    @NotNull(message = "A cidade da instituição não pode ser nula!")
    private String cidade;

    @Column(name = "bairro")
    @Size(min = 1, max = 100, message = "O bairro da instituição deve conter entre 1 e 100 caracteres")
    @NotNull(message = "O bairro da instituição não pode ser nulo!")
    private String bairro;

    @Column(name = "logradouro")
    @Size(min = 1, max = 120, message = "O logradouro da instituição deve conter entre 1 e 120 caracteres")
    @NotNull(message = "O logradouro da instituição não pode ser nulo!")
    private String logradouro;

    @Column(name = "numero")
    @NotNull(message = "O numero do endereço da instituição não pode ser nulo!")
    private int numero;

    @Column(name = "cep")
    @NotNull(message = "O cep da instituição não pode ser nulo!")
    private String cep;

    @Column(name = "status")
    @NotNull(message = "O status da instituição não pode ser nulo!")
    private StatusEnum status;

    @JoinColumn(name = "id_usuario")
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
}
