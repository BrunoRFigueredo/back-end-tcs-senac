CREATE TABLE IF NOT EXISTS projeto_social.perfil_permissao (
    id        SERIAL NOT NULL,
    perfil    VARCHAR(50) NOT NULL,
    permissao TEXT [] NOT NULL,
    PRIMARY KEY (id));

CREATE SEQUENCE projeto_social.sequence_perfil_permissao START 1 increment 1;
--
INSERT INTO projeto_social.perfil_permissao
    (id, perfil, permissao)
VALUES
    (1, 'NORMAL', '{"INSTITUICAO","PROJETO","VOLUNTARIO"}');
--
INSERT INTO projeto_social.perfil_permissao
    (id, perfil, permissao)
VALUES
    (2, 'INSTITUICAO', '{"INSTITUICAO","PROJETO","VOLUNTARIO","CATEGORIA","INSUMO","SERVICO"}');
--
INSERT INTO projeto_social.perfil_permissao
    (id, perfil, permissao)
VALUES
    (3, 'VOLUNTARIO', '{"INSTITUICAO","PROJETO","VOLUNTARIO"}');
--
CREATE TABLE IF NOT EXISTS projeto_social.usuario (
    id SERIAL NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    confirmar_senha VARCHAR(100) NOT NULL,
    dh_cadastro TIMESTAMP NOT NULL,
    status INT NOT NULL,
    id_perfil_permissao INTEGER NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_usuario_perfil_permissao
    FOREIGN KEY (id_perfil_permissao)
    REFERENCES projeto_social.perfil_permissao (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE SEQUENCE projeto_social.sequence_usuario START 1 increment 1;

CREATE TABLE IF NOT EXISTS projeto_social.instituicao(
    id         serial  not null,
    nome       varchar(80),
    descricao  varchar(256),
    cnpj       varchar(45),
    CONTA      VARCHAR(10),
    AGENCIA    VARCHAR(10),
    email      varchar(100),
    telefone   varchar(20),
    pais       varchar(20),
    estado     varchar(2),
    cidade     varchar(50),
    bairro     varchar(80),
    logradouro varchar(120),
    cep        varchar(20),
    numero     integer,
    status     integer,
    id_usuario integer,
    PRIMARY KEY (id),
    CONSTRAINT FK_USUARIO_INSTITUICAO
    FOREIGN KEY (ID_USUARIO)
    REFERENCES projeto_social.usuario (id));

CREATE SEQUENCE projeto_social.sequence_instituicao
    START 1
    INCREMENT 1;
--
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
--
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

CREATE SEQUENCE projeto_social.sequence_servico START 1 increment 1;
--
CREATE TABLE IF NOT EXISTS projeto_social.insumo(
    id        SERIAL NOT NULL,
    nome      VARCHAR(150) NOT NULL,
    descricao VARCHAR(250) NOT NULL,
    unidade_medida VARCHAR(20) NOT NULL,
    status    INT NOT NULL,
    id_categoria INTEGER NOT NULL,
    id_instituicao integer,
    PRIMARY KEY (id),
    CONSTRAINT fk_insumo_categoria
    FOREIGN KEY (id_categoria)
    REFERENCES projeto_social.categoria (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT FK_INSUMO_INSTITUICAO
    FOREIGN KEY (id_instituicao)
    REFERENCES projeto_social.instituicao (id));

);

CREATE SEQUENCE projeto_social.sequence_insumo START 1 increment 1;
--
create table if not exists projeto_social.voluntario
(
    id SERIAL  not null,
    nome       varchar(200) not null,
    biografia  text not null,
    cpf        varchar not null,
    telefone   varchar(15),
    pais       varchar(20) not null,
    estado     varchar(2) not null,
    cidade     varchar(50) not null,
    bairro     varchar(80) not null,
    logradouro varchar(120) not null,
    cep        varchar(15) not null,
    numero     integer not null,
    status     integer not null,
    id_usuario integer,
    primary KEY(id),
    constraint fk_usuario_voluntario
    foreign key(id_usuario) references projeto_social.usuario(id)
    );

CREATE SEQUENCE projeto_social.sequence_voluntario
    START 1
    INCREMENT 1;
--
CREATE TABLE IF NOT EXISTS projeto_social.imagem (
    id         serial  not null,
    arquivo       bytea,
    nome_arquivo  varchar(50),
    tipo_arquivo       varchar(50),
    PRIMARY KEY (id)
    );

CREATE SEQUENCE projeto_social.sequence_imagem
    START 1
    INCREMENT 1;

ALTER TABLE projeto_social.usuario
    ADD id_imagem INTEGER;

ALTER TABLE projeto_social.usuario
    ADD CONSTRAINT fk_usuario_imagem
        FOREIGN KEY (id_imagem)
            REFERENCES projeto_social.imagem (id);
--
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
--
CREATE TABLE IF NOT EXISTS projeto_social.projeto_servico
(
    id SERIAL NOT NULL,
    status_projeto_servico int not null,
    status_servico int not null,
    status_aprovacao int not null,
    data_inicio date not null,
    data_final date,
    id_voluntario int,
    id_projeto int not null,
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
--
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
--
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
--
CREATE TABLE IF NOT EXISTS PROJETO_SOCIAL.INSTITUICOES_PREFEITURA(
    ID         SERIAL  NOT NULL,
    NOME       VARCHAR(80),
    CNPJ       VARCHAR(45),
    EMAIL      VARCHAR(100),
    TELEFONE   VARCHAR(20),
    AGENCIA    VARCHAR(10),
    CONTA      VARCHAR(10),
    ANO_VIGENCIA_INICIO TIMESTAMP,
    ANO_VIGENCIA_FIM TIMESTAMP,
    LOGRADOURO VARCHAR(120),
    BAIRRO     VARCHAR(80),
    CEP        VARCHAR(20),
    PAIS       VARCHAR(20),
    ESTADO     VARCHAR(2),
    CIDADE     VARCHAR(50),
    NUMERO     INTEGER
    );

INSERT INTO PROJETO_SOCIAL.INSTITUICOES_PREFEITURA
(ID, NOME, CNPJ, EMAIL, TELEFONE, AGENCIA, CONTA, ANO_VIGENCIA_INICIO, ANO_VIGENCIA_FIM, LOGRADOURO, BAIRRO, CEP, PAIS, ESTADO, CIDADE, NUMERO)
VALUES
    (1, 'Grupo Unido pela Unidade Infanto Juvenil de Onco-Hematologia GUIDO', '12927890000160', 'captacao@guido.org.br',
     '4830456211', '32263', '88018', '2022-05-19', '2024-05-19', 'Rua Santo Antônio', 'Cruzeiro do Sul', '88811040', 'BR', 'SC', 'CRICIUMA', 790);
--
INSERT INTO PROJETO_SOCIAL.INSTITUICOES_PREFEITURA
(ID, NOME, CNPJ, EMAIL,
 TELEFONE, AGENCIA, CONTA, ANO_VIGENCIA_INICIO, ANO_VIGENCIA_FIM, LOGRADOURO, BAIRRO, CEP, PAIS, ESTADO, CIDADE, NUMERO)
VALUES
    (2, 'Centro de Atendimento Socioeducativo - CASE Criciúma', '13586538000171', 'casecriciuma@case.sc.gov.br',
     '48996019734', '0', '0', '2022-05-12', '2024-05-12', 'Rua Pedro Liberato Pave', 'São Domingos', '88800000', 'BR', 'SC', 'CRICIUMA', 1645);
--
