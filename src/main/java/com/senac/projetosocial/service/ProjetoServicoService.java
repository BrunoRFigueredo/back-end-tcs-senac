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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ProjetoServicoService {
    private ProjetoServicoRepository projetoServicoRepository;

    public ProjetoServico salvarProjetoServico(ProjetoServicoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                               Voluntario voluntario, Projeto projeto, Servico servico){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String texto = criarOuAtualizar.getData_inicio().format(formatter);
//        LocalDate dateParsed = LocalDate.parse(texto, formatter);

        ProjetoServico projetoServico = ProjetoServico.builder()
                .statusAprovacao(StatusAprovacaoEnum.PROCESSANDO)
                .status(StatusEnum.ATIVO)
                .statusServico(StatusServicoEnum.PENDENTE)
                .data_inicio(criarOuAtualizar.getData_inicio())
                .data_final(criarOuAtualizar.getData_final())
                .voluntario(voluntario)
                .projeto(projeto)
                .servico(servico)
                .build();

        return this.projetoServicoRepository.save(projetoServico);
    }

    public ProjetoServico buscarProjetoServico(Long id){
        BooleanExpression filtro = QProjetoServico.projetoServico.id.eq(id);

        return this.projetoServicoRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("ProjetoServico " + id + " nao encontrado"));
    }

    public ProjetoServico atualizarProjetoServico(Long id,
                                    ProjetoServicoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                                  Voluntario voluntario, Projeto projeto, Servico servico){
        ProjetoServico projetoAntigo = this.buscarProjetoServico(id);

        ProjetoServico projetoNovo = projetoAntigo.toBuilder()
//                .statusAprovacao(StatusAprovacaoEnum.PROCESSANDO)
//                .statusServico(StatusServicoEnum.PENDENTE)
                .status(StatusEnum.ATIVO)
                .data_inicio(criarOuAtualizar.getData_inicio())
                .data_final(criarOuAtualizar.getData_final())
                .voluntario(voluntario)
                .projeto(projeto)
                .servico(servico)
                .build();

        return this.projetoServicoRepository.save(projetoNovo);
    }

    public void apagarProjetoServico(Long id){
        ProjetoServico projetoServico = this.buscarProjetoServico(id);
        projetoServico.setStatus(StatusEnum.INATIVO);
        this.projetoServicoRepository.save(projetoServico);
    }

}
