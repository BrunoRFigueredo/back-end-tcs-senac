CREATE TABLE IF NOT EXISTS projeto_social.categorias (
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    int NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE projeto_social.sequence_categoria
    INCREMENT  1
    START 1;