package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.ProjetoServicoRepository;
import com.senac.projetosocial.representation.ProjetoServicoRepresentation;
import com.senac.projetosocial.service.*;
import com.senac.projetosocial.util.Paginacao;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
@RequestMapping("/projeto-servico")
@AllArgsConstructor
@CrossOrigin("*")
public class ProjetoServicoController {
    private final ProjetoService projetoService;
    private final VoluntarioService voluntarioService;
    private final ServicoService servicoService;
    private final ProjetoServicoService projetoServicoService;

    @PostMapping("/")
    public ResponseEntity<ProjetoServicoRepresentation.Detalhe> cadastrarProjeto(
            @Valid @RequestBody ProjetoServicoRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Servico servico = this.servicoService.buscarServico(criarOuAtualizar.getServico());
        Voluntario voluntario = null;
        if (Objects.nonNull(criarOuAtualizar.getVoluntario())) {
            voluntario = this.voluntarioService.buscarVoluntario(criarOuAtualizar.getVoluntario());
        }
        Projeto projeto = this.projetoService.buscarProjeto(criarOuAtualizar.getProjeto());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoServicoRepresentation.Detalhe
                        .from(projetoServicoService.salvarProjetoServico(criarOuAtualizar, voluntario, projeto, servico)));
    }

    @PutMapping("/servicos/{id}/vincular-voluntario/{idVoluntario}")
    public ResponseEntity<ProjetoServicoRepresentation.Detalhe> vincularVoluntario(
            @PathVariable("id") Long id,
            @PathVariable("idVoluntario") Long idVoluntario) {

        Voluntario voluntario = this.voluntarioService.buscarVoluntario(idVoluntario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoServicoRepresentation.Detalhe
                        .from(projetoServicoService.vincularVoluntario(id, voluntario)));
    }

    @PutMapping("/servicos/{id}/aprovar-reprovar-voluntario")
    public ResponseEntity<ProjetoServicoRepresentation.Detalhe> aprovarReprovarVoluntario(
            @PathVariable("id") Long id,
            @RequestParam String isAprovado) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoServicoRepresentation.Detalhe
                        .from(projetoServicoService.aprovarReprovarProjeto(id, Boolean.valueOf(isAprovado))));
    }


    @PutMapping("{id}/concluir-servico")
    public ResponseEntity<ProjetoServicoRepresentation.Detalhe> concluirServico(
            @PathVariable("id") Long id) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoServicoRepresentation.Detalhe
                        .from(projetoServicoService.concluirServico(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoServicoRepresentation.Detalhe> buscarProjeto(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(ProjetoServicoRepresentation.Detalhe
                        .from(this.projetoServicoService.buscarProjetoServico(id)));
    }

    @GetMapping("/servicos/{idProjeto}")
    public ResponseEntity<Paginacao> buscarProjetosServicosByProjeto(
            @PathVariable("idProjeto") Long idProjeto,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina) {

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<ProjetoServico> listaProjeto = this.projetoServicoService.buscarProjetosServicosByProjeto(idProjeto, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaProjeto.getTotalElements())
                .proximaPagina(listaProjeto.hasNext())
                .conteudo(ProjetoServicoRepresentation.Lista.from(listaProjeto.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoServicoRepresentation.Detalhe> atualizarProjeto(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProjetoServicoRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Servico servico = this.servicoService.buscarServico(criarOuAtualizar.getServico());
        Voluntario voluntario = this.voluntarioService.buscarVoluntario(criarOuAtualizar.getVoluntario());
        Projeto projeto = this.projetoService.buscarProjeto(criarOuAtualizar.getProjeto());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjetoServicoRepresentation.Detalhe
                        .from(this.projetoServicoService.atualizarProjetoServico(id, criarOuAtualizar, voluntario, projeto, servico)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity apagarProjeto(@PathVariable("id") Long id) {
        this.projetoService.apagarProjeto(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Deletado");
    }

}
