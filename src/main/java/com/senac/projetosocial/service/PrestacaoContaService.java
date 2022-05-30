package com.senac.projetosocial.service;

import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.PrestacaoConta;
import com.senac.projetosocial.repository.PrestacaoContaRepository;
import com.senac.projetosocial.representation.PrestacaoContaRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrestacaoContaService {
    private final PrestacaoContaRepository prestacaoContaRepository;

    public PrestacaoConta salvarPrestacaoConta(PrestacaoContaRepresentation.CriarOuAtualizar criarOuAtualizar, Projeto projeto) {
        PrestacaoConta prestacaoConta = PrestacaoConta.builder()
                .vlArrecadado(criarOuAtualizar.getVlArrecadado())
                .qtdAlimento(criarOuAtualizar.getQtdAlimento())
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



    public PrestacaoConta buscarPrestacaoConta(Long id) {
        return this.prestacaoContaRepository.findById(id).orElseThrow(() -> new NotFoundException("Prestação não encontrada."));
    }

    public void deletarPrestacaoConta(Long id) {
        this.buscarPrestacaoConta(id);
        this.prestacaoContaRepository.deleteById(id);
    }

}
