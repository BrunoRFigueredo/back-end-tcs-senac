CREATE TABLE IF NOT EXISTS projeto_social.categoria (
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    INT NOT NULL,
    PRIMARY KEY (id),
    id_instituicao integer,
    CONSTRAINT FK_CATEGORIA_INSTITUICAO
    FOREIGN KEY (id_instituicao)
    REFERENCES projeto_social.instituicao (id));

CREATE SEQUENCE projeto_social.sequence_categoria START 1 increment 1;
