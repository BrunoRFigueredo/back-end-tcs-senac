package com.senac.projetosocial.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_usuario")
    @SequenceGenerator(name = "sequence_usuario", sequenceName = "usuario_seq")
    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo")
    @Size(min = 1, max = 100, message = "O campo nome deve ter entre 1 a 100 caracteres")
    @Column(name = "nome", length = 100)
    private String nome;

    @NotNull(message = "O campo email não pode ser nulo")
    @Size(min = 1, max = 150, message = "O campo email deve ter entre 1 a 150 caracteres")
    @Column(name = "email", length = 150)
    private String email;

    @NotNull(message = "O campo senha não pode ser nulo")
    @Size(min = 1, max = 100, message = "O campo senha deve ter entre 1 a 100 caracteres")
    @Column(name = "password", length = 100)
    private String password;

    @NotNull(message = "O campo confirmação da senha não pode ser nulo")
    @Size(min = 1, max = 100, message = "O campo confirmação da senha deve ter entre 1 a 100 caracteres")
    @Column(name = "confirmpassword", length = 100)
    private String confirmPassword;

    @NotNull(message = "O campo data do cadastro não pode ser nulo")
    @Column(name = "dh_cadastro")
    private LocalDateTime dataHoraCadastro;

    @NotNull(message = "O campo status não pode ser nulo")
    @Column(name = "status", length = 1)
    private Status status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil_permissao")
    @NotNull(message = "O campo perfil não pode ser nulo")
    private PerfilPermissao perfilPermissao;


    public enum Status {
        ATIVO,
        INATIVO
    }


}
