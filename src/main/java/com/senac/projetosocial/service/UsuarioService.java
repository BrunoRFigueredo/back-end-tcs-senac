package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exeptions.BusinessExeption;
import com.senac.projetosocial.exeptions.NotFoundException;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.QUsuario;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.repository.UsuarioRepository;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario salvar(UsuarioRepresentation.CreateOrUpdate createOrUpdate, PerfilPermissao perfilPermissao) {

        if (!createOrUpdate.getPassword().equals(createOrUpdate.getConfirmPassword())) {
            throw new BusinessExeption("A senha não confere com a confirmação da senha");
        }

        Usuario usuario = Usuario.builder()
                .nome(createOrUpdate.getNome())
                .email(createOrUpdate.getEmail())
                .password(createOrUpdate.getPassword())
                .confirmPassword(createOrUpdate.getConfirmPassword())
                .dataHoraCadastro(LocalDateTime.now())
                .status(StatusEnum.ATIVO)
                .perfilPermissao(perfilPermissao)
                .build();

        return this.usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, UsuarioRepresentation.CreateOrUpdate createOrUpdate, PerfilPermissao perfilPermissao) {
        Usuario usuarioAntigo = this.getUsuario(id);
        Usuario usuarioAtualizado = usuarioAntigo.toBuilder()
                .nome(createOrUpdate.getNome())
                .nome(createOrUpdate.getNome())
                .email(createOrUpdate.getEmail())
                .password(createOrUpdate.getPassword())
                .confirmPassword(createOrUpdate.getConfirmPassword())
                .status(StatusEnum.ATIVO)
                .perfilPermissao(perfilPermissao)
                .build();

        return this.usuarioRepository.save(usuarioAtualizado);
    }

    public Usuario getUsuario(Long id) {
        BooleanExpression filter =
                QUsuario.usuario.id.eq(id)
                        .and(QUsuario.usuario.status.eq(StatusEnum.ATIVO));
        return this.usuarioRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = this.getUsuario(id);
        usuario.setStatus(StatusEnum.INATIVO);
        this.usuarioRepository.save(usuario);
    }


}
