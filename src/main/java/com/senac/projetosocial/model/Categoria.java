package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_categoria")
    @SequenceGenerator(name = "sequence_categoria", sequenceName = "sequence_categoria", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    @NotNull(message = "O nome da categoria não pode ser nula")
    @Size(max = 45, min = 1, message = "O nome da categoria deve conter entre 45 e 1 caracter")
    private String nome;

    @Column(name = "descricao")
    @NotNull(message = "A descrição da categoria não pode ser nula")
    @Size(max = 200, min = 1, message = "A descrição da categoria não pode ser nula")
    private String descricao;

    @Column(name = "status")
    @NotNull(message = "O status da categoria não pode ser nula")
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instituicao")
    @NotNull(message = "A instituição da categoria não pode ser nula!")
    private Instituicao instituicao;


}
