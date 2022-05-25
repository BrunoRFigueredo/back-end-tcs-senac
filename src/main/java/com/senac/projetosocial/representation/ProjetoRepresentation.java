package com.senac.projetosocial.representation;

import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.Projeto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public interface ProjetoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{
        @NotNull(message = "O nome do projeto não pode ser nulo")
        @Size(min = 1 , max = 120, message = "O nome do projeto deve conter entre 1 e 120 caracteres")
        private String nome;

        @NotNull(message = "A descricao do projeto não pode ser nulo")
        @Size(min = 1 , max = 500, message = "A descriçao do projeto deve conter entre 1 e 500 caracteres")
        private String descricao;

        private StatusEnum status;

        @NotNull(message = "A data de inicio do projeto não pode ser nulo")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate data_inicio;

        private LocalDate data_final;

        @NotNull(message = "A instituição do projeto não pode ser nula")
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
        private String data_inicio;
        private LocalDate data_final;
        private InstituicaoRepresentation.Resumo instituicao;

        public static Detalhe from(Projeto projeto){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String texto = projeto.getData_inicio().format(formatter);
            LocalDate dateParsed = LocalDate.parse(texto, formatter);

            return Detalhe.builder()
                    .id(projeto.getId())
                    .nome(projeto.getNome())
                    .descricao(projeto.getDescricao())
                    .status(projeto.getStatus())
                    .data_inicio(texto)
                    .data_final(projeto.getData_final())
                    .instituicao(InstituicaoRepresentation.Resumo.from(projeto.getInstituicao()))
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
        private String descricao;

        public static ProjetoRepresentation.Resumo from(Projeto projeto){
            return ProjetoRepresentation.Resumo.builder()
                    .id(projeto.getId())
                    .nome(projeto.getNome())
                    .descricao(projeto.getDescricao())
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
        private LocalDate data_inicio;
        private LocalDate data_final;

        private static Lista from (Projeto projeto){
            return Lista.builder()
                    .id(projeto.getId())
                    .nome(projeto.getNome())
                    .data_inicio(projeto.getData_inicio())
                    .data_final(projeto.getData_final())
                    .build();
        }

        public static List<Lista> from(List<Projeto> projetos){
            return projetos
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }

}
