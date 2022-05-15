package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.QProjeto;
import com.senac.projetosocial.repository.ProjetoRepository;
import com.senac.projetosocial.representation.ProjetoRepresentation;
import com.senac.projetosocial.service.InstituicaoService;
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
@RequestMapping("/projeto")
@AllArgsConstructor
@CrossOrigin("*")
public class ProjetoController {
    private final ProjetoService projetoService;
    private final InstituicaoService instituicaoService;
    private final ProjetoRepository projetoRepository;

    @PostMapping("/")
    public ResponseEntity<ProjetoRepresentation.Detalhe> cadastrarProjeto(
            @Valid @RequestBody ProjetoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Instituicao instituicao = this.instituicaoService.buscarInstituicao(criarOuAtualizar.getInstituicao());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoRepresentation.Detalhe
                        .from(projetoService.salvarProjeto(criarOuAtualizar, instituicao)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoRepresentation.Detalhe> buscarProjeto(@PathVariable("id") Long id){
        return ResponseEntity
                .ok(ProjetoRepresentation.Detalhe
                        .from(this.projetoService.buscarProjeto(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarProjetos(
            @QuerydslPredicate(root = Projeto.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QProjeto.projeto.status.eq(StatusEnum.ATIVO) :
                QProjeto.projeto.status.eq(StatusEnum.ATIVO)
                        .and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<Projeto> listaProjeto = this.projetoRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaProjeto.getTotalElements())
                .proximaPagina(listaProjeto.hasNext())
                .conteudo(ProjetoRepresentation.Lista.from(listaProjeto.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoRepresentation.Detalhe> atualizarProjeto(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProjetoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Instituicao instituicao = this.instituicaoService.buscarInstituicao(criarOuAtualizar.getInstituicao());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoRepresentation.Detalhe
                        .from(this.projetoService.atualizarProjeto(id, criarOuAtualizar, instituicao)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity apagarProjeto(@PathVariable("id") Long id){
        this.projetoService.apagarProjeto(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Deletado");
    }

}
