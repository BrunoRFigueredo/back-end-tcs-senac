package com.senac.projetosocial.controller;

import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.representation.PerfilPermissaoRepresentation;
import com.senac.projetosocial.service.PerfilPermissaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/perfil-permissao")
@AllArgsConstructor
@CrossOrigin("*")
public class PerfilPermissaoController {

    private final PerfilPermissaoService perfilPermissaoService;

    @PostMapping
    public ResponseEntity<PerfilPermissao> cadastrarPerfilPermissao(@Valid @RequestBody PerfilPermissaoRepresentation.CriarOuAtualizar criarOuAtualizar) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.perfilPermissaoService.salvarPerfilPermissao(criarOuAtualizar));
    }


    @GetMapping
    public ResponseEntity<Iterable<PerfilPermissao>> buscarPerfilPermissoes() {
        return ResponseEntity.ok(this.perfilPermissaoService.buscarPerfilPermissoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilPermissao> buscarPerfilPermissao(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.perfilPermissaoService.buscarPerfilPermissao(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PerfilPermissao> atualizaPerfilPermissao(@PathVariable("id") Long id,
                                                                        @Valid @RequestBody PerfilPermissaoRepresentation.CriarOuAtualizar criarOuAtualizar) {


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.perfilPermissaoService.atualizarPerfilPermissao(id, criarOuAtualizar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPerfilPermissao(@PathVariable("id") Long id) {
        this.perfilPermissaoService.deletarPerfilPermissao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
