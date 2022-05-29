package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.ProjetoInsumoRepository;
import com.senac.projetosocial.representation.CategoriaRepresentation;
import com.senac.projetosocial.representation.ProjetoInsumoRepresentation;
import com.senac.projetosocial.service.InsumoService;
import com.senac.projetosocial.service.ProjetoInsumoService;
import com.senac.projetosocial.service.ProjetoService;
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
@RequestMapping("/perfil-permissao")
@AllArgsConstructor
@CrossOrigin("*")
public class ProjetoInsumoController {
    private final ProjetoInsumoService projetoInsumoService;
    private final ProjetoService projetoService;
    private final InsumoService insumoService;
    private final ProjetoInsumoRepository projetoInsumoRepository;

    @PostMapping("/")
    public ResponseEntity<ProjetoInsumo> cadastrarProjetoInsumo(
        @Valid @RequestBody ProjetoInsumoRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Insumo insumo = this.insumoService.buscarInsumo(criarOuAtualizar.getInsumo());
        Projeto projeto = this.projetoService.buscarProjeto(criarOuAtualizar.getProjeto());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.projetoInsumoService.salvarProjetoInsumo(insumo,projeto));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarProjetoInsumo(
            @QuerydslPredicate(root = Categoria.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QCategoria.categoria.status.eq(StatusEnum.ATIVO) :
                QCategoria.categoria.status.eq(StatusEnum.ATIVO)
                        .and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<ProjetoInsumo> listaProjetoInsumo = this.projetoInsumoRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaProjetoInsumo.getTotalElements())
                .proximaPagina(listaProjetoInsumo.hasNext())
                .conteudo(ProjetoInsumoRepresentation.Lista
                        .from(listaProjetoInsumo.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoInsumo> buscarProjetoInsumo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.projetoInsumoService.buscarProjetoInsumo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoInsumo> atualizaProjetoInsumo(@PathVariable("id") Long id,
        @Valid @RequestBody ProjetoInsumoRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Insumo insumo = this.insumoService.buscarInsumo(criarOuAtualizar.getInsumo());
        Projeto projeto = this.projetoService.buscarProjeto(criarOuAtualizar.getProjeto());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.projetoInsumoService.atualizarProjetoInsumo(id, insumo,projeto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarProjetoInsumo(@PathVariable("id") Long id) {
        this.projetoInsumoService.deletarProjetoInsumo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
