package com.senac.projetosocial.model;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projeto")
    @NotNull(message = "O projeto do insumo não pode ser nulo!")
    private Projeto projeto;



}
