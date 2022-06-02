package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.QCategoria;
import com.senac.projetosocial.model.QServico;
import com.senac.projetosocial.repository.CategoriaRepository;
import com.senac.projetosocial.representation.CategoriaRepresentation;
import com.senac.projetosocial.service.CategoriaService;
import com.senac.projetosocial.service.InstituicaoService;
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
    private InstituicaoService instituicaoService;

    @PostMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detalhe> cadastrarCategoria(
            @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Instituicao instituicao = this.instituicaoService.buscarInstituicao(criarOuAtualizar.getInstituicao());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detalhe.from(this.categoriaService.salvarCategoria(criarOuAtualizar, instituicao)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detalhe> buscarCategoria(@PathVariable("id") Long id) {
        return ResponseEntity.ok(CategoriaRepresentation.Detalhe
                .from(this.categoriaService.buscarCategoria(id)));
    }

    @GetMapping("instituicao/{idInstituicao}")
    public ResponseEntity<Paginacao> buscarCategorias(
            @QuerydslPredicate(root = Categoria.class) Predicate filtroURI,
            @PathVariable("idInstituicao") Long idInstituicao,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina) {

        BooleanExpression where = QCategoria.categoria.status.eq(StatusEnum.ATIVO).and(QCategoria.categoria.instituicao().id.eq(idInstituicao));
        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                where :
                where.and(filtroURI);

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
                                                                              @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizar criarOuAtualizar) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CategoriaRepresentation.Detalhe
                        .from(this.categoriaService.atualizarCategoria(id, criarOuAtualizar)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarCategoria(@PathVariable("id") Long id) {
        this.categoriaService.deletarCategoria(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
