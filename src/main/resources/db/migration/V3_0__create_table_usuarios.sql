CREATE TABLE IF NOT EXISTS projeto_social.usuario (
  id SERIAL NOT NULL,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL,
  password VARCHAR(100) NOT NULL,
  confirmPassword VARCHAR(100) NOT NULL,
  dh_cadastro TIMESTAMP NOT NULL,
  status VARCHAR(1) NOT NULL,
  id_perfil_permissao INTEGER NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_usuario_perfil_permissao
    FOREIGN KEY (id_perfil_permissao)
    REFERENCES projeto_social.perfil_permissao (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE SEQUENCE projeto_social.sequence_usuario START 1;
