package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.QCategoria;
import com.senac.projetosocial.repository.CategoriaRepository;
import com.senac.projetosocial.representation.CategoriaRepresentation;
import com.senac.projetosocial.service.CategoriaService;
import com.senac.projetosocial.util.Paginacao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
@CrossOrigin("*")
public class CategoriaController {
    private CategoriaService categoriaService;
    private CategoriaRepository categoriaRepository;

    @PostMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detalhe> cadastrarCategoria(
            @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detalhe.from(this.categoriaService.salvarCategoria(criarOuAtualizar)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detalhe> buscarCategoria(@PathVariable("id") Long id){
        return ResponseEntity.ok(CategoriaRepresentation.Detalhe
                .from(this.categoriaService.buscarCategoria(id)));
    }
    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarCategorias(
            @QuerydslPredicate(root = Categoria.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO) :
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO)
                        .and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<Categoria> listaCategoria = this.categoriaRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaCategoria.getTotalElements())
                .proximaPagina(listaCategoria.hasNext())
                .conteudo(CategoriaRepresentation.Lista
                        .from(listaCategoria.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detalhe> atualizarCategoria(@PathVariable("id") Long id,
        @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CategoriaRepresentation.Detalhe
                        .from(this.categoriaService.atualizarCategoria(id, criarOuAtualizar)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletarCategoria(@PathVariable("id") Long id){
        this.categoriaService.deletarCategoria(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
