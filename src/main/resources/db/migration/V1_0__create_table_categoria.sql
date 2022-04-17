CREATE TABLE IF NOT EXISTS projeto_social.categoria (
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    VARCHAR(1) NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE projeto_social.sequence_categoria START 1;
