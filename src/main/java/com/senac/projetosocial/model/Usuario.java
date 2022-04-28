package com.senac.projetosocial.model;

import com.senac.projetosocial.enums.StatusEnum;
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
    @SequenceGenerator(name = "sequence_usuario", sequenceName = "sequence_usuario", allocationSize = 1)
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
    @Column(name = "senha", length = 100)
    private String senha;

    @NotNull(message = "O campo confirmação da senha não pode ser nulo")
    @Size(min = 1, max = 100, message = "O campo confirmação da senha deve ter entre 1 a 100 caracteres")
    @Column(name = "confirmar_senha", length = 100)
    private String confirmarSenha;

    @NotNull(message = "O campo data do cadastro não pode ser nulo")
    @Column(name = "dh_cadastro")
    private LocalDateTime dataHoraCadastro;

    @NotNull(message = "O campo status não pode ser nulo")
    @Column(name = "status", length = 1)
    private StatusEnum status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil_permissao")
    @NotNull(message = "O campo perfil não pode ser nulo")
    private PerfilPermissao perfilPermissao;




}
