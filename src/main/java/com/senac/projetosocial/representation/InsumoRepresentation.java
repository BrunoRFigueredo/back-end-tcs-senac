package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.Insumo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface InsumoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar {

        @NotNull(message = "O nome do insumo não pode ser nulo")
        @Size(max = 150, min = 1, message = "O nome do insumo deve conter entre 45 e 1 caracter")
        private String nome;

        @NotNull(message = "A descrição do insumo não pode ser nulo")
        @Size(max = 250, min = 1, message = "A descrição do insumo não pode ser nulo")
        private String descricao;

        @NotNull(message = "A unidade de medida do insumo não pode ser nulo")
        @Size(max = 20, min = 1, message = "A unidade de medida do insumo não pode ser nulo")
        private String unidadeMedida;

        @NotNull(message = "A categoria é obrigatório")
        private Long categoria;

        @NotNull(message = "A instituição do insumo não pode ser nula")
        private Long instituicao;

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe {
        private Long id;
        private String nome;
        private String descricao;
        private String unidadeMedida;
        private Categoria categoria;
        private StatusEnum status;

        public static Detalhe from(Insumo insumo) {
            return Detalhe.builder()
                    .id(insumo.getId())
                    .nome(insumo.getNome())
                    .descricao(insumo.getDescricao())
                    .unidadeMedida(insumo.getUnidadeMedida())
                    .categoria(insumo.getCategoria())
                    .status(insumo.getStatus())
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String nome;

        private static Lista from(Insumo insumo) {
            return Lista.builder()
                    .id(insumo.getId())
                    .nome(insumo.getNome())
                    .build();
        }

        public static List<Lista> from(List<Insumo> insumos) {
            return insumos
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
