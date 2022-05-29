CREATE TABLE IF NOT EXISTS projeto_social.projeto_servico
(
    id SERIAL NOT NULL,
    status_projeto_servico int not null,
    status_servico int not null,
    status_aprovacao int not null,
    data_inicio date not null,
    data_final date,
    id_voluntario int not null,
    id_projeto int,
    id_servico int not null,
    PRIMARY KEY(id),
    CONSTRAINT FK_SERVICO_PROJETO
    FOREIGN KEY(id_projeto) REFERENCES PROJETO(id),
    CONSTRAINT FK_SERVICO_SERVICO
    FOREIGN KEY(id_servico) REFERENCES SERVICO(id),
    CONSTRAINT FK_SERVICO_VOLUNTARIO
    FOREIGN KEY(id_voluntario) REFERENCES VOLUNTARIO(id));

CREATE SEQUENCE projeto_social.sequence_projeto_servico
    START 1
    INCREMENT 1;