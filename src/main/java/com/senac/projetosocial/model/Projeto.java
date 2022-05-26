package com.senac.projetosocial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senac.projetosocial.enums.StatusEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "projeto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_projeto")
    @SequenceGenerator(name = "sequence_projeto", sequenceName = "sequence_projeto", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    @NotNull(message = "O nome do projeto não pode ser nulo")
    @Size(min = 1 , max = 120, message = "O nome do projeto deve conter entre 1 e 120 caracteres")
    private String nome;

    @Column(name = "descricao")
    @NotNull(message = "A descricao do projeto não pode ser nulo")
    @Size(min = 1 , max = 500, message = "A descriçao do projeto deve conter entre 1 e 500 caracteres")
    private String descricao;

    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "data_inicio")
    @NotNull(message = "A data de inicio do projeto não pode ser nulo")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @Column(name = "data_final")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFinal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_instituicao")
    @NotNull(message = "A instituição do projeto não pode ser nula!")
    private Instituicao instituicao;
}
