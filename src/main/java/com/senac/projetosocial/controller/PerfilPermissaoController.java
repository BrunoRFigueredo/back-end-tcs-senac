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
    public ResponseEntity<PerfilPermissao> cadastrarPerfilPermissao(@Valid @RequestBody PerfilPermissaoRepresentation.CreateOrUpdate createOrUpdate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.perfilPermissaoService.salvar(createOrUpdate));
    }


    @GetMapping
    public ResponseEntity<Iterable<PerfilPermissao>> getAllPerfilPermissao() {
        return ResponseEntity.ok(this.perfilPermissaoService.getAllPerfilPermissao());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilPermissao> getOnePerfilPermissao(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.perfilPermissaoService.getPerfilPermissao(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PerfilPermissao> atualizaPerfilPermissao(@PathVariable("id") Long id,
                                                                        @Valid @RequestBody PerfilPermissaoRepresentation.CreateOrUpdate createOrUpdate) {


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.perfilPermissaoService.update(id, createOrUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerfilPermissao(@PathVariable("id") Long id) {
        this.perfilPermissaoService.deletePerfilPermissao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
