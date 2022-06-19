package com.senac.projetosocial.controller;

import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.ProjetoInsumo;
import com.senac.projetosocial.repository.ProjetoInsumoRepository;
import com.senac.projetosocial.representation.ProjetoInsumoRepresentation;
import com.senac.projetosocial.service.InsumoService;
import com.senac.projetosocial.service.ProjetoInsumoService;
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
@RequestMapping("/projeto-insumo")
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
                .body(this.projetoInsumoService.salvarProjetoInsumo(insumo,projeto, criarOuAtualizar));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarProjetoInsumo(
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<ProjetoInsumo> listaProjetoInsumo = this.projetoInsumoRepository.findAll(pagina);

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

    @GetMapping("/insumos/{idProjeto}")
    public ResponseEntity<Paginacao> buscarProjetosInsumosByProjeto(
            @PathVariable("idProjeto") Long idProjeto,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina) {

        Pageable paginaInsumosProjeto = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<ProjetoInsumo> listaInsumosProjeto = this.projetoInsumoService.buscarProjetosInsumosByProjeto(idProjeto, paginaInsumosProjeto);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaInsumosProjeto.getTotalElements())
                .proximaPagina(listaInsumosProjeto.hasNext())
                .conteudo(ProjetoInsumoRepresentation.Lista.from(listaInsumosProjeto.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<ProjetoInsumo> concluirProjetoInsumo(@PathVariable("id") Long id) {
        this.projetoInsumoService.concluirProjetoInsumo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

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
                .body(this.projetoInsumoService.atualizarProjetoInsumo(id, insumo,projeto, criarOuAtualizar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarProjetoInsumo(@PathVariable("id") Long id) {
        this.projetoInsumoService.deletarProjetoInsumo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
