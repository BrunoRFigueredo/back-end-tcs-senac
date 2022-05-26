
package com.senac.projetosocial.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.exceptions.BusinessExeption;
import com.senac.projetosocial.model.QUsuario;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.repository.UsuarioRepository;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import com.senac.projetosocial.service.PerfilPermissaoService;
import com.senac.projetosocial.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario/")
@AllArgsConstructor
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity<Usuario> cadastrarUsuario(
            @Valid @RequestBody UsuarioRepresentation.CriarOuAtualizar criarOuAtualizar) {
        BooleanExpression filtro = QUsuario.usuario.email.eq(criarOuAtualizar.getEmail());

        List<Usuario> usuario = this.usuarioRepository.findAll(filtro);

        if (usuario.size() >= 1) {
            throw new BusinessExeption("Já existe um usuário cadatro com este email");
        } else if (!criarOuAtualizar.getSenha().equals(criarOuAtualizar.getConfirmarSenha())) {
            throw new BusinessExeption("A senha está diferente da confirmaçao de senha");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.usuarioService.criarUsuario(criarOuAtualizar));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRepresentation.Detail> buscarUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(UsuarioRepresentation.Detail.from(this.usuarioService.getUsuario(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRepresentation.Detail> atualizaPerfilPermissao(@PathVariable("id") Long id,
                                                                                @Valid @RequestBody UsuarioRepresentation.CriarOuAtualizar criarOuAtualizar) {
        Usuario usuario = this.usuarioService.getUsuario(id);

        if (!usuario.getEmail().equals(criarOuAtualizar.getEmail())) {
            BooleanExpression filtro = QUsuario.usuario.email.eq(criarOuAtualizar.getEmail());
            Optional<Usuario> usuarioAtualizado = this.usuarioRepository.findOne(filtro);

            if (usuarioAtualizado.isPresent()) {
                throw new BusinessExeption("Já existe um usuário cadastrado com esse e-mail!");
            }
        }

        if (!criarOuAtualizar.getSenha().equals(criarOuAtualizar.getConfirmarSenha())) {
            throw new BusinessExeption("A senha está diferente da confirmação de senha!");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(UsuarioRepresentation.Detail.from(this.usuarioService.update(id, criarOuAtualizar)));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarPerfilPermissao(@PathVariable("id") Long id) {
        this.usuarioService.deleteUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
