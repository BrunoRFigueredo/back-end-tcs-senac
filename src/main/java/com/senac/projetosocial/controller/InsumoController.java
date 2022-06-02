package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.InsumoRepository;
import com.senac.projetosocial.representation.InsumoRepresentation;
import com.senac.projetosocial.service.CategoriaService;
import com.senac.projetosocial.service.InstituicaoService;
import com.senac.projetosocial.service.InsumoService;
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
@RequestMapping("/insumo")
@AllArgsConstructor
@CrossOrigin("*")
public class InsumoController {
    private InsumoService insumoService;
    private InsumoRepository insumoRepository;
    private CategoriaService categoriaService;
    private InstituicaoService instituicaoService;

    @PostMapping("/")
    public ResponseEntity<InsumoRepresentation.Detalhe> cadastrarInsumo(
            @Valid @RequestBody InsumoRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Categoria categoria = this.categoriaService.buscarCategoria(criarOuAtualizar.getCategoria());
        Instituicao instituicao = this.instituicaoService.buscarInstituicao(criarOuAtualizar.getInstituicao());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InsumoRepresentation.Detalhe.from(this.insumoService.salvarInsumo(criarOuAtualizar, categoria, instituicao)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoRepresentation.Detalhe> buscarInsumo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(InsumoRepresentation.Detalhe
                .from(this.insumoService.buscarInsumo(id)));
    }

    @GetMapping("instituicao/{idInstituicao}")
    public ResponseEntity<Paginacao> buscarInsumos(
            @QuerydslPredicate(root = Insumo.class) Predicate filtroURI,
            @PathVariable("idInstituicao") Long idInstituicao,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina) {

        BooleanExpression where = QInsumo.insumo.status.eq(StatusEnum.ATIVO).and(QInsumo.insumo.instituicao().id.eq(idInstituicao));
        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                where :
                where.and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<Insumo> listaInsumo = this.insumoRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaInsumo.getTotalElements())
                .proximaPagina(listaInsumo.hasNext())
                .conteudo(InsumoRepresentation.Lista
                        .from(listaInsumo.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoRepresentation.Detalhe> atualizarInsumo(@PathVariable("id") Long id,
                                                                        @Valid @RequestBody InsumoRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Categoria categoria = this.categoriaService.buscarCategoria(criarOuAtualizar.getCategoria());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(InsumoRepresentation.Detalhe
                        .from(this.insumoService.atualizarInsumo(id, criarOuAtualizar, categoria)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarInsumo(@PathVariable("id") Long id) {
        this.insumoService.deletarInsumo(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
