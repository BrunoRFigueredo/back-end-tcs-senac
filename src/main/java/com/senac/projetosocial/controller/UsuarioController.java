package com.senac.projetosocial.controller;

import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import com.senac.projetosocial.service.PerfilPermissaoService;
import com.senac.projetosocial.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PerfilPermissaoService perfilPermissaoService;

    @PostMapping("/")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioRepresentation.CriarOuAtualizar criarOuAtualizar) {
        PerfilPermissao perfilPermissao = this.perfilPermissaoService.buscarPerfilPermissao(criarOuAtualizar.getPerfilPermissao());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.usuarioService.salvar(criarOuAtualizar, perfilPermissao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRepresentation.Detail> buscarUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(UsuarioRepresentation.Detail.from(this.usuarioService.getUsuario(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRepresentation.Detail> atualizaPerfilPermissao(@PathVariable("id") Long id,
                                                                   @Valid @RequestBody UsuarioRepresentation.CriarOuAtualizar criarOuAtualizar) {
        PerfilPermissao perfilPermissao = this.perfilPermissaoService.buscarPerfilPermissao(criarOuAtualizar.getPerfilPermissao());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UsuarioRepresentation.Detail.from(this.usuarioService.update(id, criarOuAtualizar, perfilPermissao)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPerfilPermissao(@PathVariable("id") Long id) {
        this.usuarioService.deleteUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
