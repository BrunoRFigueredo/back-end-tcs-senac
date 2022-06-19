package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.PrestacaoContaRepository;
import com.senac.projetosocial.representation.PrestacaoContaRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrestacaoContaService {
    private final PrestacaoContaRepository prestacaoContaRepository;

    public PrestacaoConta salvarPrestacaoConta(PrestacaoContaRepresentation.CriarOuAtualizar criarOuAtualizar, Projeto projeto) {
        PrestacaoConta prestacaoConta = PrestacaoConta.builder()
                .vlArrecadado(criarOuAtualizar.getVlArrecadado())
                .qtdAlimento(criarOuAtualizar.getQtdAlimento())
                .dataPrestacao(criarOuAtualizar.getDataPrestacao())
                .statusEnum(StatusEnum.ATIVO)
                .projeto(projeto)
                .build();

        return this.prestacaoContaRepository.save(prestacaoConta);
    }

    public PrestacaoConta atualizarPrestacaoConta(Long id, PrestacaoContaRepresentation.CriarOuAtualizar criarOuAtualizar, Projeto projeto) {
        PrestacaoConta prestacaoContaAntigo = this.buscarPrestacaoConta(id);
        PrestacaoConta prestacaoContaAtualizado = prestacaoContaAntigo.toBuilder()
                .vlArrecadado(criarOuAtualizar.getVlArrecadado())
                .qtdAlimento(criarOuAtualizar.getQtdAlimento())
                .projeto(projeto)
                .build();

        return this.prestacaoContaRepository.save(prestacaoContaAtualizado);
    }

    public Page<PrestacaoConta> buscarPrestacaoContaByProjeto(Long idProjeto, Pageable pageable) {
        BooleanExpression filtro = QPrestacaoConta.prestacaoConta.projeto().id.eq(idProjeto)
                .and(QPrestacaoConta.prestacaoConta.statusEnum.eq(StatusEnum.ATIVO));

        return this.prestacaoContaRepository.findAll(filtro, pageable);
    }


    public PrestacaoConta buscarPrestacaoConta(Long id) {
        return this.prestacaoContaRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestação não encontrada."));
    }

    public void deletarPrestacaoConta(Long id) {
        PrestacaoConta prestacaoConta = this.buscarPrestacaoConta(id);
        PrestacaoConta newPrestacaoConta = prestacaoConta.toBuilder().statusEnum(StatusEnum.INATIVO).build();
        this.prestacaoContaRepository.save(newPrestacaoConta);
    }

}
