package com.senac.projetosocial.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.BusinessException;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.QInstituicao;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.repository.InstituicaoRepository;
import com.senac.projetosocial.representation.InstituicaoRepresentation;
import com.senac.projetosocial.service.InstituicaoService;
import com.senac.projetosocial.service.UsuarioService;
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
@RequestMapping("/instituicao")
@AllArgsConstructor
@CrossOrigin("*")
public class InstituicaoController {
    private final InstituicaoService instituicaoService;
    private final UsuarioService usuarioService;
    private final InstituicaoRepository instituicaoRepository;

    @PostMapping("/")
    public ResponseEntity<InstituicaoRepresentation.Detalhe> cadastrarInstituicao(
            @Valid @RequestBody InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Boolean existeInstitituicaoByUsuario = instituicaoService.existeInstitituicaoByUsuario(criarOuAtualizar.getUsuario());

        if(existeInstitituicaoByUsuario){
            throw new BusinessException("Já existe uma instituição cadastrada para este usuário");
        }

        Usuario usuario = this.usuarioService.getUsuario(criarOuAtualizar.getUsuario());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InstituicaoRepresentation.Detalhe.from(this.instituicaoService.cadastrarInstituicao(criarOuAtualizar, usuario)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoRepresentation.Detalhe> buscarInstituicao(@PathVariable("id") Long id){
        return ResponseEntity
                .ok(InstituicaoRepresentation.Detalhe
                        .from(this.instituicaoService.buscarInstituicao(id)));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<InstituicaoRepresentation.Resumo> buscarInstituicaoUsuario(@PathVariable("idUsuario") Long idUsuario){
            return ResponseEntity
                    .ok(InstituicaoRepresentation.Resumo
                            .from(this.instituicaoService.buscarInstituicaoUsuario(idUsuario)));
        }

    @GetMapping("/")
    public ResponseEntity<Paginacao> buscarInstituicoes(
            @QuerydslPredicate(root = Instituicao.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "5") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int numeroPagina){

        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QInstituicao.instituicao.status.eq(StatusEnum.ATIVO) :
                QInstituicao.instituicao.status.eq(StatusEnum.ATIVO)
                        .and(filtroURI);

        Pageable pagina = PageRequest.of(numeroPagina, tamanhoPagina);

        Page<Instituicao> listaInstituicao = this.instituicaoRepository.findAll(filtro, pagina);

        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(numeroPagina)
                .tamanhoPagina(tamanhoPagina)
                .totalRegistros(listaInstituicao.getTotalElements())
                .proximaPagina(listaInstituicao.hasNext())
                .conteudo(InstituicaoRepresentation.Lista
                        .from(listaInstituicao.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoRepresentation.Detalhe> atualizarInstituicao(@PathVariable("id") Long id,
            @Valid @RequestBody InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar){

        Usuario usuario = this.usuarioService.getUsuario(criarOuAtualizar.getUsuario());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InstituicaoRepresentation.Detalhe.from(this.instituicaoService.atualizarInstituicao(id, criarOuAtualizar, usuario)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity apagarInstituicao(@PathVariable("id") Long id){
        this.instituicaoService.apagarInstituicao(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
