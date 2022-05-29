CREATE TABLE IF NOT EXISTS projeto_social.projeto_insumo
(
    id SERIAL NOT NULL,
    id_projeto int not null,
    id_insumo int not null,
    PRIMARY KEY(id),
    CONSTRAINT FK_INSUMO_INSUMO
    FOREIGN KEY(id_insumo) REFERENCES INSUMO(id),
    CONSTRAINT FK_PROJETO_INSUMO
    FOREIGN KEY(id_projeto) REFERENCES PROJETO(id));

CREATE SEQUENCE projeto_social.sequence_projeto_insumo
    START 1
    INCREMENT 1;