CREATE TABLE IF NOT EXISTS projeto_social.projeto
(
    id SERIAL NOT NULL,
    nome varchar(120) not null,
    descricao text not null,
    status int not null,
    data_inicio date not null,
    data_final date,
    id_instituicao int not null,
    PRIMARY KEY(id),
    CONSTRAINT FK_PROJETO_INSTITUICAO
    FOREIGN KEY(id_instituicao) REFERENCES INSTITUICAO(id));

CREATE SEQUENCE projeto_social.sequence_projeto
    START 1
    INCREMENT 1;