package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.model.QVoluntario;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.model.Voluntario;
import com.senac.projetosocial.repository.VoluntarioRepository;
import com.senac.projetosocial.representation.VoluntarioRepresentation;
import com.senac.projetosocial.service.UsuarioService;
import com.senac.projetosocial.service.VoluntarioService;
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
@RequestMapping("/voluntario")
@AllArgsConstructor
@CrossOrigin("*")
public class VoluntarioController {
    private final VoluntarioService voluntarioService;
    private final UsuarioService usuarioService;
    private final VoluntarioRepository voluntarioRepository;

    @PostMapping("/")
    public ResponseEntity<VoluntarioRepresentation.Detalhe> cadastrarVoluntario(
            @Valid @RequestBody VoluntarioRepresentation.CriarOuAtualizar criarOuAtualizar){

        Usuario usuario = this.usuarioService.getUsuario(criarOuAtualizar.getUsuario());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(VoluntarioRepresentation.Detalhe
                        .from(this.voluntarioService.salvarVoluntario(criarOuAtualizar, usuario)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioRepresentation.Detalhe> buscarVoluntario(@PathVariable("id") Long id){
        return ResponseEntity.ok(VoluntarioRepresentation.Detalhe.from(this.voluntarioService.buscarVoluntario(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarVoluntarios(
            @QuerydslPredicate(root = Voluntario.class)Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QVoluntario.voluntario.status.eq(StatusEnum.ATIVO) :
                QVoluntario.voluntario.status.eq(StatusEnum.ATIVO)
                        .and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<Voluntario> listaVoluntario = this.voluntarioRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .proximaPagina(listaVoluntario.hasNext())
                .totalRegistros(listaVoluntario.getTotalElements())
                .conteudo(VoluntarioRepresentation.Lista
                        .from(listaVoluntario.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);

    }

    @PutMapping("/{id}")
    public ResponseEntity<VoluntarioRepresentation.Detalhe> atualizarVoluntario(
            @Valid @RequestBody VoluntarioRepresentation.CriarOuAtualizar criarOuAtualizar,
            @PathVariable("id") Long id){

        Usuario usuario = this.usuarioService.getUsuario(criarOuAtualizar.getUsuario());

        return ResponseEntity.ok(VoluntarioRepresentation.Detalhe.from(this.voluntarioService.atualizarVoluntario(id, criarOuAtualizar, usuario)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity apagarVoluntario(@PathVariable("id") Long id){
        this.voluntarioService.apagarVoluntario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
