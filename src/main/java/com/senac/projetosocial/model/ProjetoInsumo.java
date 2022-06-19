package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusInsumoEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "projeto_insumo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProjetoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_projeto_insumo")
    @SequenceGenerator(name = "sequence_projeto_insumo", sequenceName = "sequence_projeto_insumo", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_insumo")
    @NotNull(message = "O insumo do projeto não pode ser nulo!")
    private Insumo insumo;

    @Column(name = "quantidade")
    @NotNull(message = "A quantidade de insumos do projeto não pode ser nulo!")
    private Double quantidade;

    @Column(name = "status")
    @NotNull(message = "O status do insumo do projeto não pode ser nulo!")
    private StatusInsumoEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projeto")
    @NotNull(message = "O projeto do insumo não pode ser nulo!")
    private Projeto projeto;



}
