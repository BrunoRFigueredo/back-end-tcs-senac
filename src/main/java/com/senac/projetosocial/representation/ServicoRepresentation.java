package com.senac.projetosocial.representation;

import com.senac.projetosocial.model.Servico;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface ServicoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizar{
        @NotNull(message = "O nome do serviço não pode ser nulo")
        @Size(max = 45, min = 1, message = "O nome do serviço deve conter entre 1 e 45 caracteres")
        private String nome;

        @NotNull(message = "A descrição do serviço não pode ser nulo")
        @Size(max = 200, min = 1, message = "A descrição do serviço deve conter entre 1 e 45 caracteres")
        private String descricao;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhe{
        private Long id;
        private String nome;
        private String descricao;
        private Servico.Status status;

        public static Detalhe from (Servico servico){
            return Detalhe.builder()
                    .id(servico.getId())
                    .nome(servico.getNome())
                    .descricao(servico.getDescricao())
                    .status(servico.getStatus())
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

        private static Lista from (Servico servico){
            return Lista.builder()
                    .id(servico.getId())
                    .nome(servico.getNome())
                    .build();
        }
        public static List<Lista> from(List<Servico> servicos){
            return servicos
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
