CREATE TABLE IF NOT EXISTS projeto_social.servico(
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE projeto_social.sequence_servico START 1 increment 1;
