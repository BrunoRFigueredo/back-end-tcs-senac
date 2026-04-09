package com.senac.projetosocial.service;

import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.exceptions.UnauthorizedException;
import com.senac.projetosocial.jwt.TokenService;
import com.senac.projetosocial.model.QUsuario;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.repository.UsuarioRepository;
import com.senac.projetosocial.representation.LoginRepresentation;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPassword;

    public LoginRepresentation.LoginResponse login(Usuario usuario){
        // verificar depois
        String roles = usuario.getPerfilPermissao().getPerfil();

        return LoginRepresentation.LoginResponse.builder()
                .roles(roles)
                .usuario(UsuarioRepresentation.UsuarioToken.from(usuario))
                .tokenType("Bearer")
                .token(this.tokenService.generateToken(UsuarioRepresentation.UsuarioToken.from(usuario), roles))
                .build();
    }

    public LoginRepresentation.LoginResponse login(LoginRepresentation.Login login){
        Usuario usuario = this.usuarioRepository.findOne(QUsuario.usuario.email.eq(login.getEmail()))
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (this.bCryptPassword.matches(login.getPassword(), usuario.getSenha())){
            return this.login(usuario);
        } else{
            throw new UnauthorizedException("A senha informada está errada");
        }
    }
}
