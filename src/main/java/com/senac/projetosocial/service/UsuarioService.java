package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.BusinessExeption;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.QPerfilPermissao;
import com.senac.projetosocial.model.QUsuario;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.repository.PerfilPermissaoRepository;
import com.senac.projetosocial.repository.UsuarioRepository;
import com.senac.projetosocial.representation.LoginRepresentation;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPassword;
    private PerfilPermissaoRepository perfilPermissaoRepository;
    private PerfilPermissaoService perfilPermissaoService;

    public Usuario criarUsuario(UsuarioRepresentation.CriarOuAtualizar criarOuAtualizar) {

        Usuario novoUsuario = Usuario.builder()
                .nome(criarOuAtualizar.getNome())
                .email(criarOuAtualizar.getEmail())
                .senha(this.bCryptPassword.encode(criarOuAtualizar.getSenha()))
                .confirmarSenha(this.bCryptPassword.encode(criarOuAtualizar.getConfirmarSenha()))
                .dataHoraCadastro(LocalDateTime.now())
                .status(StatusEnum.ATIVO)
                .perfilPermissao(this.perfilPermissaoService.buscarPerfilPermissao(criarOuAtualizar.getPerfilPermissao()))
                .build();

        return this.usuarioRepository.save(novoUsuario);
    }

    public Usuario update(Long id, UsuarioRepresentation.CriarOuAtualizar criarOuAtualizar) {
        Usuario usuarioAntigo = this.getUsuario(id);
        Usuario usuarioAtualizado = usuarioAntigo.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .nome(criarOuAtualizar.getNome())
                .email(criarOuAtualizar.getEmail())
                .senha(this.bCryptPassword.encode(criarOuAtualizar.getSenha()))
                .confirmarSenha(this.bCryptPassword.encode(criarOuAtualizar.getConfirmarSenha()))
                .status(StatusEnum.ATIVO)
                .perfilPermissao(this.perfilPermissaoService.buscarPerfilPermissao(criarOuAtualizar.getPerfilPermissao()))
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
