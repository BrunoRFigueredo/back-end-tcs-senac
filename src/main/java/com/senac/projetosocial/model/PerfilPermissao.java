package com.senac.projetosocial.model;

import com.senac.projetosocial.util.CustomStringArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity(name = "perfil_permissao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PerfilPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_perfil_permissao")
    @SequenceGenerator(name = "sequence_perfil_permissao", sequenceName = "sequence_perfil_permissao", allocationSize = 1)
    private Long id;

    @Column(name = "perfil", length = 50)
    private String perfil;

    @Column(name = "permissao", columnDefinition = "text[]")
    @Type(type = "com.senac.projetosocial.util.CustomStringArrayType")
    private String[] permissao;

}
