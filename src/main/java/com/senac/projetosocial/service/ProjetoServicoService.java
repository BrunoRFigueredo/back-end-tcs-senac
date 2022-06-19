package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusAprovacaoEnum;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.enums.StatusServicoEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.ProjetoServicoRepository;
import com.senac.projetosocial.representation.ProjetoServicoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjetoServicoService {
    private ProjetoServicoRepository projetoServicoRepository;

    public ProjetoServico salvarProjetoServico(ProjetoServicoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                               Voluntario voluntario, Projeto projeto, Servico servico) {

        ProjetoServico projetoServico = ProjetoServico.builder()
                .statusAprovacao(StatusAprovacaoEnum.PROCESSANDO)
                .status(StatusEnum.ATIVO)
                .statusServico(StatusServicoEnum.PENDENTE)
                .dataInicio(criarOuAtualizar.getDataInicio())
                .dataFinal(criarOuAtualizar.getDataFinal())
                .voluntario(voluntario)
                .projeto(projeto)
                .servico(servico)
                .build();

        return this.projetoServicoRepository.save(projetoServico);
    }

    public Page<ProjetoServico> buscarProjetosServicosByProjeto(Long idProjeto, Pageable pageable) {
        BooleanExpression filtro = QProjetoServico.projetoServico.projeto().id.eq(idProjeto)
                .and(QProjetoServico.projetoServico.status.eq(StatusEnum.ATIVO));

        return this.projetoServicoRepository.findAll(filtro, pageable);
    }

    public ProjetoServico buscarProjetoServico(Long id) {
        BooleanExpression filtro = QProjetoServico.projetoServico.id.eq(id);

        return this.projetoServicoRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("ProjetoServico " + id + " nao encontrado"));
    }

    public ProjetoServico atualizarProjetoServico(Long id,
                                                  ProjetoServicoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                                  Voluntario voluntario, Projeto projeto, Servico servico) {
        ProjetoServico projetoAntigo = this.buscarProjetoServico(id);

        ProjetoServico projetoNovo = projetoAntigo.toBuilder()
                .status(StatusEnum.ATIVO)
                .dataInicio(criarOuAtualizar.getDataInicio())
                .dataFinal(criarOuAtualizar.getDataFinal())
                .voluntario(voluntario)
                .projeto(projeto)
                .servico(servico)
                .build();

        return this.projetoServicoRepository.save(projetoNovo);
    }


    public ProjetoServico concluirServico(Long id) {
        ProjetoServico projetoAntigo = this.buscarProjetoServico(id);

        ProjetoServico.ProjetoServicoBuilder projetoServicoBuilder = projetoAntigo.toBuilder();
        ProjetoServico projetoConcluido = projetoServicoBuilder
                .statusServico(StatusServicoEnum.CONCLUIDO)
                .build();

        return this.projetoServicoRepository.save(projetoConcluido);
    }

    public ProjetoServico aprovarReprovarProjeto(Long id,
                                                 Boolean isAprovado) {
        ProjetoServico projetoAntigo = this.buscarProjetoServico(id);

        ProjetoServico.ProjetoServicoBuilder projetoServicoBuilder = projetoAntigo.toBuilder();
        ProjetoServico projetoNovo = projetoServicoBuilder
                .status(StatusEnum.ATIVO)
                .statusAprovacao(StatusAprovacaoEnum.APROVADO)
                .statusServico(StatusServicoEnum.EM_ANDAMENTO)
                .build();
        if (!isAprovado) {
            projetoNovo = projetoServicoBuilder
                    .status(StatusEnum.ATIVO)
                    .statusAprovacao(StatusAprovacaoEnum.REPROVADO)
                    .statusServico(StatusServicoEnum.PENDENTE)
                    .voluntario(null)
                    .build();
        }


        return this.projetoServicoRepository.save(projetoNovo);
    }

    public ProjetoServico vincularVoluntario(Long id,
                                             Voluntario voluntario) {
        ProjetoServico projetoAntigo = this.buscarProjetoServico(id);
        ProjetoServico.ProjetoServicoBuilder projetoServicoBuilder = projetoAntigo.toBuilder();
        ProjetoServico projetoNovo = projetoServicoBuilder
                .voluntario(voluntario)
                .build();

        return this.projetoServicoRepository.save(projetoNovo);
    }



    public void apagarProjetoServico(Long id) {
        ProjetoServico projetoServico = this.buscarProjetoServico(id);
        projetoServico.setStatus(StatusEnum.INATIVO);
        this.projetoServicoRepository.save(projetoServico);
    }

}
