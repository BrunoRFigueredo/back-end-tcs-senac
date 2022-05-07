CREATE TABLE IF NOT EXISTS projeto_social.instituicao(
    id         serial  not null,
    nome       varchar(80),
    descricao  varchar(256),
    cnpj       varchar(45),
    pix        varchar(45),
    email      varchar(100),
    telefone   integer,
    whatsapp   integer,
    pais       varchar(20),
    estado     varchar(2),
    cidade     varchar(50),
    bairro     varchar(80),
    logradouro varchar(120),
    numero     integer,
    cep        integer,
    status     integer,
    id_usuario integer,
    PRIMARY KEY (id),
    CONSTRAINT FK_USUARIO_INSTITUICAO
    FOREIGN KEY (ID_USUARIO)
    REFERENCES projeto_social.usuario (id));

CREATE SEQUENCE projeto_social.sequence_instituicao
    START 1
    INCREMENT 1;