package com.senac.projetosocial.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_imagem")
    @SequenceGenerator(name = "sequence_imagem", sequenceName = "sequence_imagem", allocationSize = 1)
    private Long id;

    @Column(name = "arquivo")
    private byte[] file;

    @Column(name = "nome_arquivo")
    private String fileName;

    @Column(name = "tipo_arquivo")
    private String fileType;


}
