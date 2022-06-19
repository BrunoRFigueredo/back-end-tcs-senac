package com.senac.projetosocial.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusAprovacaoEnum;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.enums.StatusInsumoEnum;
import com.senac.projetosocial.enums.StatusServicoEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.ProjetoInsumoRepository;
import com.senac.projetosocial.representation.ProjetoInsumoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjetoInsumoService {
    private final ProjetoInsumoRepository projetoInsumoRepository;

    public ProjetoInsumo salvarProjetoInsumo(Insumo insumo, Projeto projeto, ProjetoInsumoRepresentation.CriarOuAtualizar criarOuAtualizar) {
        System.out.println(criarOuAtualizar.getStatus());
        ProjetoInsumo projetoInsumo = ProjetoInsumo.builder()
                .insumo(insumo)
                .projeto(projeto)
                .quantidade(criarOuAtualizar.getQuantidade())
                .status(criarOuAtualizar.getStatus())
                .build();

        return this.projetoInsumoRepository.save(projetoInsumo);
    }

    public ProjetoInsumo atualizarProjetoInsumo(Long id, Insumo insumo, Projeto projeto, ProjetoInsumoRepresentation.CriarOuAtualizar criarOuAtualizar) {
        ProjetoInsumo projetoInsumoAntigo = this.buscarProjetoInsumo(id);
        ProjetoInsumo projetoInsumoAtualizado = projetoInsumoAntigo.toBuilder()
                .insumo(insumo)
                .projeto(projeto)
                .quantidade(criarOuAtualizar.getQuantidade())
                .status(criarOuAtualizar.getStatus())
                .build();

        return this.projetoInsumoRepository.save(projetoInsumoAtualizado);
    }

    public Page<ProjetoInsumo> buscarProjetosInsumosByProjeto(Long idProjeto, Pageable pageable) {
        BooleanExpression filtroInsumo = QProjetoInsumo.projetoInsumo.projeto().id.eq(idProjeto)
                .and(QProjetoInsumo.projetoInsumo.status.eq(StatusInsumoEnum.EM_ANDAMENTO));

        return this.projetoInsumoRepository.findAll(filtroInsumo, pageable);
    }

    public ProjetoInsumo buscarProjetoInsumo(Long id) {
        return this.projetoInsumoRepository.findById(id).orElseThrow(() -> new NotFoundException("Insumo não encontrado."));
    }

    public void deletarProjetoInsumo(Long id) {
        this.buscarProjetoInsumo(id);
        this.projetoInsumoRepository.deleteById(id);
    }

}
