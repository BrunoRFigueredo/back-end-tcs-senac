package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.QServico;
import com.senac.projetosocial.model.Servico;
import com.senac.projetosocial.repository.ServicoRepository;
import com.senac.projetosocial.representation.ServicoRepresentation;
import com.senac.projetosocial.service.ServicoService;
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
@RequestMapping("servico")
@AllArgsConstructor
@CrossOrigin("*")
public class ServicoController {
    private ServicoService servicoService;
    private ServicoRepository servicoRepository;

    @PostMapping("/")
    public ResponseEntity<ServicoRepresentation.Detalhe> cadastrarServico(
        @Valid @RequestBody ServicoRepresentation.CriarOuAtualizar criarOuAtualizar){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ServicoRepresentation.Detalhe.from(this.servicoService.salvarServico(criarOuAtualizar)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServicoRepresentation.Detalhe> buscarServico(@PathVariable("id") Long id){
        return ResponseEntity
                .ok(ServicoRepresentation.Detalhe.from(this.servicoService.buscarServico(id)));
    }
    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarServicos(
            @QuerydslPredicate(root = Servico.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QServico.servico.status.eq(StatusEnum.ATIVO) :
                QServico.servico.status.eq(StatusEnum.ATIVO)
                        .and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<Servico> listaServico = this.servicoRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaServico.getTotalElements())
                .proximaPagina(listaServico.hasNext())
                .conteudo(ServicoRepresentation.Lista
                        .from(listaServico.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ServicoRepresentation.Detalhe> atualizarServico(@PathVariable("id") Long id,
        @Valid @RequestBody ServicoRepresentation.CriarOuAtualizar criarOuAtualizar){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ServicoRepresentation.Detalhe
                        .from(this.servicoService.atualizarServico(id, criarOuAtualizar)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletarServico(@PathVariable("id") Long id){
        this.servicoService.deletarServico(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
