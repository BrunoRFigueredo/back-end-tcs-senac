CREATE TABLE IF NOT EXISTS projeto_social.categoria (
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE projeto_social.sequence_categoria START 1 increment 1;
