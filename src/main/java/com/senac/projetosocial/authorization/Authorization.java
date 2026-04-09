package com.senac.projetosocial.authorization;

import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "autorizacao")
public class Authorization {
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_perfil_permissao")
    private PerfilPermissao perfil_permissao;
}
