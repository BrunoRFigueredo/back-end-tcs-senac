package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "insumo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_insumo")
    @SequenceGenerator(name = "sequence_insumo", sequenceName = "sequence_insumo", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    @NotNull(message = "O nome do insumo não pode ser nulo")
    @Size(max = 150, min = 1, message = "O nome do insumo deve conter entre 45 e 1 caracter")
    private String nome;

    @Column(name = "descricao")
    @NotNull(message = "A descrição do insumo não pode ser nulo")
    @Size(max = 250, min = 1, message = "A descrição do insumo não pode ser nulo")
    private String descricao;

    @Column(name = "unidade_medida")
    @NotNull(message = "A unidade de medida do insumo não pode ser nulo")
    @Size(max = 20, min = 1, message = "A unidade de medida do insumo não pode ser nulo")
    private String unidadeMedida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    @NotNull(message = "O campo categoria não pode ser nulo")
    private Categoria categoria;

    @Column(name = "status")
    @NotNull(message = "O status do insumo não pode ser nulo")
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instituicao")
    @NotNull(message = "A instituição do insumo não pode ser nula!")
    private Instituicao instituicao;

}
