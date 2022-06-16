ALTER TABLE projeto_social.voluntario DROP COLUMN NOME;

ALTER TABLE projeto_social.voluntario ADD data_nascimento date NOT NULL ;

ALTER TABLE projeto_social.voluntario ADD sexo integer NOT NULL ;