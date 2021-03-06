package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Categoria;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface CategoriaRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{
        @NotNull(message = "O nome da categoria não pode ser nula")
        @Size(max = 45, min = 1, message = "O nome da descrição deve conter entre 45 e 1 caracter")
        private String nome;

        @NotNull(message = "A descrição da categoria não pode ser nula")
        @Size(max = 200, min = 1, message = "A descrição da categoria não pode ser nula")
        private String descricao;

        @NotNull(message = "A instituição da categoria não pode ser nula!")
        private Long instituicao;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe{
        private Long id;
        private String nome;
        private String descricao;
        private StatusEnum status;
        private InstituicaoRepresentation.Resumo instituicao;

        public static Detalhe from (Categoria categoria){
            return Detalhe.builder()
                    .id(categoria.getId())
                    .nome(categoria.getNome())
                    .descricao(categoria.getDescricao())
                    .status(categoria.getStatus())
                    .instituicao(InstituicaoRepresentation.Resumo.from(categoria.getInstituicao()))
                    .build();
        }
    }
    @Data
    @Getter
    @Setter
    @Builder
    class Resumo{
        private Long id;
        private String nome;
        public static Resumo from (Categoria categoria){
            return Resumo.builder()
                    .id(categoria.getId())
                    .nome(categoria.getNome())
                    .build();
        }
    }


    @Data
    @Getter
    @Setter
    @Builder
    class Lista{
        private Long id;
        private String nome;
        private String descricao;


        private static Lista from (Categoria categoria){
            return Lista.builder()
                    .id(categoria.getId())
                    .nome(categoria.getNome())
                    .descricao(categoria.getDescricao())
                    .build();
        }
        public static List<Lista> from(List<Categoria> categorias){
            return categorias
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
