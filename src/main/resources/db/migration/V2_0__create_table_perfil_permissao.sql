CREATE TABLE IF NOT EXISTS projeto_social.perfil_permissao (
    id        SERIAL NOT NULL,
    perfil    VARCHAR(50) NOT NULL,
    permissao TEXT [] NOT NULL,
    PRIMARY KEY (id));

CREATE SEQUENCE projeto_social.sequence_perfil_permissao START 1;
