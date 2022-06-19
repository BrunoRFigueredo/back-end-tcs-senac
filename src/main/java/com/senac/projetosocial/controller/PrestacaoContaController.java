package com.senac.projetosocial.controller;

import com.senac.projetosocial.model.PrestacaoConta;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.ProjetoServico;
import com.senac.projetosocial.representation.PrestacaoContaRepresentation;
import com.senac.projetosocial.representation.ProjetoServicoRepresentation;
import com.senac.projetosocial.service.PrestacaoContaService;
import com.senac.projetosocial.service.ProjetoService;
import com.senac.projetosocial.util.Paginacao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/prestacao-conta")
@AllArgsConstructor
@CrossOrigin("*")
public class PrestacaoContaController {
    private final PrestacaoContaService prestacaoContaService;
    private final ProjetoService projetoService;

    @PostMapping("/")
    public ResponseEntity<PrestacaoConta> cadastrarPrestacaoConta(
        @Valid @RequestBody PrestacaoContaRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Projeto projeto = this.projetoService.buscarProjeto(criarOuAtualizar.getProjeto());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.prestacaoContaService.salvarPrestacaoConta(criarOuAtualizar,projeto));
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<PrestacaoConta> buscarPrestacaoConta(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.prestacaoContaService.buscarPrestacaoConta(id));
    }


    @GetMapping("/projeto/{idProjeto}")
    public ResponseEntity<Paginacao> buscarPrestacaoContaByProjeto(
            @PathVariable("idProjeto") Long idProjeto,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina) {

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<PrestacaoConta> listaProjeto = this.prestacaoContaService.buscarPrestacaoContaByProjeto(idProjeto, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaProjeto.getTotalElements())
                .proximaPagina(listaProjeto.hasNext())
                .conteudo(PrestacaoContaRepresentation.Lista.from(listaProjeto.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestacaoConta> atualizaPrestacaoConta(@PathVariable("id") Long id,
        @Valid @RequestBody PrestacaoContaRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Projeto projeto = this.projetoService.buscarProjeto(criarOuAtualizar.getProjeto());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.prestacaoContaService.atualizarPrestacaoConta(id, criarOuAtualizar,projeto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPrestacaoConta(@PathVariable("id") Long id) {
        this.prestacaoContaService.deletarPrestacaoConta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
