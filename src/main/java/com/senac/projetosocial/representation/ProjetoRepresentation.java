package com.senac.projetosocial.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senac.projetosocial.enums.StatusEnum;
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
        private LocalDate dataInicio;

        private LocalDate dataFinal;

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
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataInicio;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataFinal;
        private InstituicaoRepresentation.Resumo instituicao;

        public static Detalhe from(Projeto projeto){
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataInicio = projeto.getDataInicio().format(formatador);
            LocalDate dataInicioFormatada = LocalDate.parse(dataInicio, formatador);

            LocalDate dataFinalFormatada = null;

            if (projeto.getDataFinal() != null) {
                String dataFinal = projeto.getDataFinal().format(formatador);
                dataFinalFormatada = LocalDate.parse(dataFinal, formatador);
            }

            return Detalhe.builder()
                    .id(projeto.getId())
                    .nome(projeto.getNome())
                    .descricao(projeto.getDescricao())
                    .status(projeto.getStatus())
                    .dataInicio(dataInicioFormatada)
                    .dataFinal(dataFinalFormatada)
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
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate data_inicio;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate data_final;

        private static Lista from (Projeto projeto){
            return Lista.builder()
                    .id(projeto.getId())
                    .nome(projeto.getNome())
                    .data_inicio(projeto.getDataInicio())
                    .data_final(projeto.getDataFinal())
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
