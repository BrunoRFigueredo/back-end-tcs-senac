CREATE TABLE IF NOT EXISTS projeto_social.servico(
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    INT NOT NULL,
    PRIMARY KEY (id),
    id_instituicao integer,
    CONSTRAINT FK_SERVICO_INSTITUICAO
    FOREIGN KEY (id_instituicao)
    REFERENCES projeto_social.instituicao (id));

);

CREATE SEQUENCE projeto_social.sequence_servico START 1 increment 1;
