package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusInsumoEnum;
import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.ProjetoInsumo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public interface ProjetoInsumoRepresentation {

    @Data
    @Getter
    @Setter
    class CriarOuAtualizar {

        @NotNull(message = "O insumo do projeto não pode ser nulo!")
        private Long insumo;

        @NotNull(message = "O projeto do insumo não pode ser nulo!")
        private Long projeto;

        @NotNull(message = "A quantidade de insumos do projeto não pode ser nulo!")
        private Double quantidade;

        @NotNull(message = "O status do insumo do projeto não pode ser nulo!")
        private StatusInsumoEnum status;
    }

    @Data
    @Getter
    @Builder
    class Detalhes {
        private Long id;
        private Insumo insumo;
        private Projeto projeto;

        public static Detalhes from(ProjetoInsumo projetoInsumo) {
            return Detalhes.builder()
                    .id(projetoInsumo.getId())
                    .insumo(projetoInsumo.getInsumo())
                    .projeto(projetoInsumo.getProjeto())
                    .build();
        }
    }

    @Data
    @Getter
    @Builder
    class Lista {
        private Long id;
        private Double quantidade;
        private StatusInsumoEnum status;
        private Insumo insumo;
        private String nomeInsumo;
        private Projeto projeto;

        private static Lista from(ProjetoInsumo projetoInsumo) {
            return Lista.builder()
                    .id(projetoInsumo.getId())
                    .quantidade(projetoInsumo.getQuantidade())
                    .status(projetoInsumo.getStatus())
                    .projeto(projetoInsumo.getProjeto())
                    .nomeInsumo(projetoInsumo.getInsumo().getNome())
                    .build();
        }

        public static List<Lista> from(List<ProjetoInsumo> projetoInsumos) {
            return projetoInsumos
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
