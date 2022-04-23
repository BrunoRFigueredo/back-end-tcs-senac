package com.senac.projetosocial.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "SERVICO")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_servico")
    @SequenceGenerator(name = "sequence_servico", sequenceName = "sequence_servico", allocationSize = 1)
    private Long id;

    @Column(name= "nome")
    @NotNull(message = "O nome do serviço não pode ser nulo")
    @Size(max = 45, min = 1, message = "O nome do serviço deve conter entre 1 e 45 caracteres")
    private String nome;

    @Column(name= "descricao")
    @NotNull(message = "A descrição do serviço não pode ser nulo")
    @Size(max = 200, min = 1, message = "A descrição do serviço deve conter entre 1 e 45 caracteres")
    private String descricao;

    @Column(name= "status")
    @NotNull(message = "O status do serviço não pode ser nulo")
    private Status status;

    public enum Status{
        INATIVO,
        ATIVO
    }
}
