CREATE TABLE IF NOT EXISTS projeto_social.insumo(
    id        SERIAL NOT NULL,
    nome      VARCHAR(150) NOT NULL,
    descricao VARCHAR(250) NOT NULL,
    unidade_medida VARCHAR(20) NOT NULL,
    status    INT NOT NULL,
    id_categoria INTEGER NOT NULL,
    CONSTRAINT fk_insumo_categoria
    FOREIGN KEY (id_categoria)
    REFERENCES projeto_social.categoria (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    PRIMARY KEY (id)
);

CREATE SEQUENCE projeto_social.sequence_insumo START 1 increment 1;
