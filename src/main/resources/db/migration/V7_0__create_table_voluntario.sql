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

