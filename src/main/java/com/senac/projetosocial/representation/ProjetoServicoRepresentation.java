package com.senac.projetosocial.representation;

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
import java.util.List;
import java.util.stream.Collectors;

public interface ProjetoServicoRepresentation {



    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{

        @NotNull(message = "A data de inicio do serviço não pode ser nulo")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate data_inicio;

        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate data_final;

        @NotNull(message = "A instituição do projeto não pode ser nula")
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
        private LocalDate data_inicio;
        private LocalDate data_final;
        private VoluntarioRepresentation.Resumo voluntario;
        private ProjetoRepresentation.Resumo projeto;
        private ServicoRepresentation.Resumo servico;

        public static Detalhe from(ProjetoServico projetoServico){

            return Detalhe.builder()
                    .id(projetoServico.getId())
                    .data_inicio(projetoServico.getData_inicio())
                    .data_final(projetoServico.getData_final())
                    .status(projetoServico.getStatus())
                    .statusServico(projetoServico.getStatusServico())
                    .statusAprovacao(projetoServico.getStatusAprovacao())
                    .voluntario(VoluntarioRepresentation.Resumo.from(projetoServico.getVoluntario()))
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
        private String nome;
        private LocalDate data_inicio;
        private LocalDate data_final;

        private static Lista from (ProjetoServico projetoServico){
            return Lista.builder()
                    .id(projetoServico.getId())
                    .data_inicio(projetoServico.getData_inicio())
                    .data_final(projetoServico.getData_final())
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
