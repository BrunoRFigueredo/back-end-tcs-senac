package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusAprovacaoEnum;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.enums.StatusServicoEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "projeto_servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProjetoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_projeto_servico")
    @SequenceGenerator(name = "sequence_projeto_servico", sequenceName = "sequence_projeto_servico", allocationSize = 1)
    private Long id;

    @Column(name = "data_inicio")
    @NotNull(message = "A data de inicio do serviço não pode ser nulo")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_inicio;

    @Column(name = "data_final")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_final;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_voluntario")
    @NotNull(message = "A voluntário do serviço não pode ser nula!")
    private Voluntario voluntario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projeto")
    private Projeto projeto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servico")
    @NotNull(message = "O serviço não pode ser nulo!")
    private Servico servico;

    @Column(name = "status_projeto_servico")
    private StatusEnum status;

    @Column(name = "status_servico")
    private StatusServicoEnum statusServico;

    @Column(name = "status_aprovacao")
    private StatusAprovacaoEnum statusAprovacao;

}
