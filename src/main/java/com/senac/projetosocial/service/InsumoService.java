package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.QInsumo;
import com.senac.projetosocial.repository.InsumoRepository;
import com.senac.projetosocial.representation.InsumoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InsumoService {
    private InsumoRepository insumoRepository;

    public Insumo salvarInsumo(InsumoRepresentation.CriarOuAtualizar criarOuAtualizar, Categoria categoria, Instituicao instituicao) {
        return this.insumoRepository.save(Insumo.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .unidadeMedida(criarOuAtualizar.getUnidadeMedida())
                .categoria(categoria)
                .instituicao(instituicao)
                .status(StatusEnum.ATIVO)
                .build());
    }

    public Insumo buscarInsumo(Long id) {
        BooleanExpression filtro = QInsumo.insumo.id.eq(id)
                .and(QInsumo.insumo.status.eq(StatusEnum.ATIVO));
        return this.insumoRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Insumo " + id + " não encontrada!"));
    }

    public Insumo atualizarInsumo(Long id, InsumoRepresentation.CriarOuAtualizar criarOuAtualizar, Categoria categoria) {
        Insumo antigaInsumo = this.buscarInsumo(id);

        Insumo novoInsumo = antigaInsumo.toBuilder()
                .nome(criarOuAtualizar.getNome() == null ? antigaInsumo.getNome() : criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao() == null ? antigaInsumo.getDescricao() : criarOuAtualizar.getDescricao())
                .unidadeMedida(criarOuAtualizar.getUnidadeMedida() == null ? antigaInsumo.getUnidadeMedida() : criarOuAtualizar.getUnidadeMedida())
                .categoria(categoria)
                .status(StatusEnum.ATIVO)
                .build();

        return this.insumoRepository.save(novoInsumo);
    }

    public void deletarInsumo(Long id) {
        Insumo insumo = this.buscarInsumo(id);
        insumo.setStatus(StatusEnum.INATIVO);
        this.insumoRepository.save(insumo);
    }
}
