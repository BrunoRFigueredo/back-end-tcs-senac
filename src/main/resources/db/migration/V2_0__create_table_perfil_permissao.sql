CREATE TABLE IF NOT EXISTS perfil_permissao (
    id        SERIAL NOT NULL,
    perfil    VARCHAR(50) NOT NULL,
    permissao TEXT NOT NULL,
    PRIMARY KEY (id))