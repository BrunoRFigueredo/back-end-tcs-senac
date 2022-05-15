CREATE TABLE IF NOT EXISTS projeto_social.imagem (
    id         serial  not null,
    arquivo       bytea,
    nome_arquivo  varchar(50),
    tipo_arquivo       varchar(50),
    PRIMARY KEY (id)
);

CREATE SEQUENCE projeto_social.sequence_imagem
    START 1
    INCREMENT 1;

    ALTER TABLE projeto_social.usuario
    ADD id_imagem INTEGER;

    ALTER TABLE projeto_social.usuario
    ADD CONSTRAINT fk_usuario_imagem
    FOREIGN KEY (id_imagem)
    REFERENCES projeto_social.imagem (id);