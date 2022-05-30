package com.senac.projetosocial.representation;

import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.PrestacaoConta;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.ProjetoInsumo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface PrestacaoContaRepresentation {

    @Data
    @Getter
    @Setter
    class CriarOuAtualizar {

        @NotNull(message = "O insumo do projeto não pode ser nulo!")
        private BigDecimal vlArrecadado;

        @NotNull(message = "A quantidade de alimento não pode ser nulo!")
        private Integer qtdAlimento;


        @NotNull(message = "O projeto da prestação de contas não pode ser nulo!")
        private Long projeto;

    }

    @Data
    @Getter
    @Builder
    class Detalhes {

        private Long id;
        private BigDecimal vlArrecadado;
        private Integer qtdAlimento;

        public static Detalhes from(PrestacaoConta prestacaoConta) {
            return Detalhes.builder()
                    .id(prestacaoConta.getId())
                    .vlArrecadado(prestacaoConta.getVlArrecadado())
                    .qtdAlimento(prestacaoConta.getQtdAlimento())
                    .build();
        }
    }

    @Data
    @Getter
    @Builder
    class Lista {
        private Long id;
        private BigDecimal vlArrecadado;
        private Integer qtdAlimento;

        private static PrestacaoConta from(PrestacaoConta prestacaoConta) {
            return PrestacaoConta.builder()
                    .id(prestacaoConta.getId())
                    .vlArrecadado(prestacaoConta.getVlArrecadado())
                    .qtdAlimento(prestacaoConta.getQtdAlimento())
                    .build();
        }

        public static List<PrestacaoConta> from(List<PrestacaoConta> prestacaoContas) {
            return prestacaoContas
                    .stream()
                    .map(PrestacaoContaRepresentation.Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
