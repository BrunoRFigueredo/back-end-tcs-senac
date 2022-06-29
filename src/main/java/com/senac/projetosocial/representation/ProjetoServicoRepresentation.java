package com.senac.projetosocial.representation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senac.projetosocial.enums.StatusAprovacaoEnum;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.enums.StatusServicoEnum;
import com.senac.projetosocial.model.ProjetoServico;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface ProjetoServicoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{

        @NotNull(message = "A data de inicio do serviço não pode ser nulo")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataInicio;

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataFinal;

        private Long voluntario;

        @NotNull(message = "O projeto do serviço não pode ser nulo!")
        private Long projeto;

        @NotNull(message = "O serviço não pode ser nulo!")
        private Long servico;

        private StatusEnum status;

        private StatusServicoEnum statusServico;

        private StatusAprovacaoEnum statusAprovacao;

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe{
        private Long id;
        private StatusEnum status;
        private StatusServicoEnum statusServico;
        private StatusAprovacaoEnum statusAprovacao;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataInicio;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataFinal;
        private VoluntarioRepresentation.Resumo voluntario;
        private ProjetoRepresentation.Resumo projeto;
        private ServicoRepresentation.Resumo servico;

        public static Detalhe from(ProjetoServico projetoServico){

            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataInicio = projetoServico.getDataInicio().format(formatador);
            LocalDate dataInicioFormatada = LocalDate.parse(dataInicio, formatador);

            LocalDate dataFinalFormatada = null;

            if (projetoServico.getDataFinal() != null) {
                String dataFinal = projetoServico.getDataFinal().format(formatador);
                dataFinalFormatada = LocalDate.parse(dataFinal, formatador);
            }

            return Detalhe.builder()
                    .id(projetoServico.getId())
                    .dataInicio(dataInicioFormatada)
                    .dataFinal(dataFinalFormatada)
                    .status(projetoServico.getStatus())
                    .statusServico(projetoServico.getStatusServico())
                    .statusAprovacao(projetoServico.getStatusAprovacao())
                    .voluntario(Objects.isNull(projetoServico.getVoluntario()) ? null : VoluntarioRepresentation.Resumo.from(projetoServico.getVoluntario()))
                    .voluntario(Objects.isNull(projetoServico.getVoluntario()) ? null : VoluntarioRepresentation.Resumo.from(projetoServico.getVoluntario()))
                    .projeto(ProjetoRepresentation.Resumo.from(projetoServico.getProjeto()))
                    .servico(ServicoRepresentation.Resumo.from(projetoServico.getServico()))
                    .build();
        }
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista{
        private Long id;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataInicio;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dataFinal;
        private Long idVoluntario;
        private String nomeVoluntario;
        private String nomeServico;
        private StatusServicoEnum statusServicoEnum;

        private static Lista from (ProjetoServico projetoServico){
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataInicio = projetoServico.getDataInicio().format(formatador);
            LocalDate dataInicioFormatada = LocalDate.parse(dataInicio, formatador);

            LocalDate dataFinalFormatada = null;

            if (projetoServico.getDataFinal() != null) {
                String dataFinal = projetoServico.getDataFinal().format(formatador);
                dataFinalFormatada = LocalDate.parse(dataFinal, formatador);
            }

            return Lista.builder()
                    .id(projetoServico.getId())
                    .dataInicio(dataInicioFormatada)
                    .dataFinal(dataFinalFormatada)
                    .idVoluntario(Objects.isNull(projetoServico.getVoluntario()) ? null : projetoServico.getVoluntario().getId())
                    .nomeVoluntario(Objects.isNull(projetoServico.getVoluntario()) ? "Sem voluntário" : projetoServico.getVoluntario().getUsuario().getNome())
                    .nomeServico(projetoServico.getServico().getNome())
                    .statusServicoEnum(projetoServico.getStatusServico())
                    .build();
        }

        public static List<Lista> from(List<ProjetoServico> projetoServicos){
            return projetoServicos
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }

}
