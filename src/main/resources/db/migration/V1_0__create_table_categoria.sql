CREATE TABLE IF NOT EXISTS categoria (
    id        SERIAL NOT NULL,
    nome      VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    status    VARCHAR(1) NOT NULL,
    PRIMARY KEY (id)
)