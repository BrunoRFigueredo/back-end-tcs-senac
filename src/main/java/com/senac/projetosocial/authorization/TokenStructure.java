package com.senac.projetosocial.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.representation.UsuarioRepresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenStructure {
    @JsonProperty("sub")
    private String sub;

    @JsonProperty("usuario")
    private UsuarioRepresentation.UsuarioToken usuario;

    @JsonProperty("roles")
    private String roles;

    @JsonProperty("exp")
    private Long exp;

    @JsonProperty("iat")
    private Long iat;

    @JsonProperty("iss")
    private String iss;
}
