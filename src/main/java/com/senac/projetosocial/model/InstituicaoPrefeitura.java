package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "instituicoes_prefeitura")
public class InstituicaoPrefeitura {
    @Id
    private Long id;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private String agencia;
    private String conta;
    private LocalDateTime anoVigenciaInicio;
    private LocalDateTime anoVigenciaFim;
    private String logradouro;
    private String bairro;
    private String cep;
    private String pais;
    private String estado;
    private String cidade;
    private Long numero;
}
