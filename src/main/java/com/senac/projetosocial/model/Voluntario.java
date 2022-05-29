package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "voluntario")
public class Voluntario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_voluntario")
    @SequenceGenerator(name = "sequence_voluntario", sequenceName = "sequence_voluntario", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    @NotNull(message = "O nome do voluntario não pode ser nulo!")
    @Size(min = 1, max = 200, message = "O nome do voluntario deve conter entre 1 e 200 caracteres")
    private String nome;

    @Column(name = "biografia")
    @NotNull(message = "A biografia do voluntario não pode ser nula!")
    @Size(min = 1, max = 255, message = "A biografia do voluntario deve conter entre 1 e 255 caracteres")
    private String biografia;

    @Column(name = "cpf")
    @NotNull(message = "O cpf da instituição não pode ser nulo!")
    @Size(min = 1, max = 11, message = "O cpf do voluntario deve conter entre 1 e 11 caracteres")
    private String cpf;

    @Column(name = "telefone")
    @NotNull(message = "O telefone não pode ser nulo!")
    private String telefone;

    @Column(name = "pais")
    @Size(min = 1, max = 20, message = "O pais do voluntario deve conter entre 1 e 20 caracteres")
    @NotNull(message = "O pais da instituição não pode ser nulo!")
    private String pais;

    @Column(name = "estado")
    @Size(min = 1, max = 2, message = "O estado do voluntario deve conter 1 ou 2 caracteres")
    @NotNull(message = "O estado da instituição não pode ser nulo!")
    private String estado;

    @Column(name = "cidade")
    @Size(min = 1, max = 50, message = "A cidade do voluntario deve conter entre 1 e 50 caracteres")
    @NotNull(message = "A cidade da instituição não pode ser nula!")
    private String cidade;

    @Column(name = "bairro")
    @Size(min = 1, max = 80, message = "O bairro do voluntário deve conter entre 1 e 80 caracteres")
    @NotNull(message = "O bairro do voluntário nao pode ser nulo")
    private String bairro;

    @Column(name = "logradouro")
    @Size(min = 1, max = 120, message = "O logradouro do voluntario deve conter entre 1 e 120 caracteres")
    @NotNull(message = "O logradouro da instituição não pode ser nulo!")
    private String logradouro;

    @Column(name = "numero")
    @NotNull(message = "O numero do endereço do voluntario não pode ser nulo!")
    private Long numero;

    @Column(name = "cep")
    @NotNull(message = "O cep do voluntario não pode ser nulo!")
    private Long cep;

    @Column(name = "status")
    @NotNull(message = "O status do voluntario não pode ser nulo!")
    private StatusEnum status;

    @JoinColumn(name = "id_usuario")
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
}
