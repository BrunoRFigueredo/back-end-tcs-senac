package com.senac.projetosocial.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "prestacao_conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PrestacaoConta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_prestacao_conta")
    @SequenceGenerator(name = "sequence_prestacao_conta", sequenceName = "sequence_prestacao_conta", allocationSize = 1)
    private Long id;

    @Column(name = "vl_arrecadado")
    @NotNull(message = "O insumo do projeto não pode ser nulo!")
    private BigDecimal vl_arrecadado;

    @Column(name = "qtd_alimento")
    @NotNull(message = "A quantidade de alimento não pode ser nulo!")
    private Integer qtd_alimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projeto")
    @NotNull(message = "O projeto da prestação de contas não pode ser nulo!")
    private Projeto projeto;


}
