package com.senac.projetosocial.representation;

import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.ProjetoInsumo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
        private Insumo insumo;
        private Projeto projeto;

        private static ProjetoInsumo from(ProjetoInsumo projetoInsumo) {
            return ProjetoInsumo.builder()
                    .id(projetoInsumo.getId())
                    .insumo(projetoInsumo.getInsumo())
                    .projeto(projetoInsumo.getProjeto())
                    .build();
        }

        public static List<ProjetoInsumo> from(List<ProjetoInsumo> projetoInsumos) {
            return projetoInsumos
                    .stream()
                    .map(ProjetoInsumoRepresentation.Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
