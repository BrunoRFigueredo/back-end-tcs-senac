package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.QCategoria;
import com.senac.projetosocial.repository.CategoriaRepository;
import com.senac.projetosocial.representation.CategoriaRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar){
        return this.categoriaRepository.save(Categoria.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());
    }
    public Categoria buscarCategoria(Long id){
        BooleanExpression filtro = QCategoria.categoria.id.eq(id)
                .and(QCategoria.categoria.status.eq(Categoria.Status.ATIVO));

            return this.categoriaRepository.findOne(filtro)
                    .orElseThrow(() -> new NotFoundException("Categoria " + id + " não encontrada!"));
    }

    public Categoria atualizarCategoria(Long id, CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar){
        Categoria oldCategoria = this.buscarCategoria(id);

        Categoria newCategoria = oldCategoria.toBuilder()
                .nome(criarOuAtualizar.getNome() == null ? oldCategoria.getNome() : criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao() == null ? oldCategoria.getDescricao() : criarOuAtualizar.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build();

        return this.categoriaRepository.save(newCategoria);
    }

    public void deletarCategoria(Long id){
        Categoria categoria = this.buscarCategoria(id);
        categoria.setStatus(Categoria.Status.INATIVO);
        this.categoriaRepository.save(categoria);
    }
}
