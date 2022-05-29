CREATE TABLE IF NOT EXISTS projeto_social.prestacao_conta
(
    id SERIAL NOT NULL,
    vl_arrecadado NUMERIC(20,2) NOT NULL,
    qtd_alimento INTEGER NOT NULL,
    id_projeto INTEGER NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT FK_PROJETO_PRESTACAO
    FOREIGN KEY(id_projeto) REFERENCES PROJETO(id));

    CREATE SEQUENCE projeto_social.sequence_prestacao_conta
        START 1
        INCREMENT 1;