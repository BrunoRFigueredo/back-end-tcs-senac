package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.QCategoria;
import com.senac.projetosocial.repository.CategoriaRepository;
import com.senac.projetosocial.representation.CategoriaRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar, Instituicao instituicao) {
        return this.categoriaRepository.save(Categoria.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .status(StatusEnum.ATIVO)
                .instituicao(instituicao)
                .build());
    }

    public Categoria buscarCategoria(Long id) {
        BooleanExpression filtro = QCategoria.categoria.id.eq(id)
                .and(QCategoria.categoria.status.eq(StatusEnum.ATIVO));

        return this.categoriaRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Categoria " + id + " não encontrada!"));
    }

    public Categoria atualizarCategoria(Long id, CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar) {
        Categoria antigaCategoria = this.buscarCategoria(id);

        Categoria novaCategoria = antigaCategoria.toBuilder()
                .nome(criarOuAtualizar.getNome() == null ? antigaCategoria.getNome() : criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao() == null ? antigaCategoria.getDescricao() : criarOuAtualizar.getDescricao())
                .status(StatusEnum.ATIVO)
                .build();

        return this.categoriaRepository.save(novaCategoria);
    }

    public void deletarCategoria(Long id) {
        Categoria categoria = this.buscarCategoria(id);
        categoria.setStatus(StatusEnum.INATIVO);
        this.categoriaRepository.save(categoria);
    }
}
