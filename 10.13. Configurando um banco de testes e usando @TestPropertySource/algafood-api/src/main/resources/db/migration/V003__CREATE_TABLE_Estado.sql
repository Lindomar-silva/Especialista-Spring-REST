CREATE TABLE estado(
	id bigint NOT NULL AUTO_INCREMENT,
	nome varchar(80) NOT NULL,
	PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

INSERT INTO estado (nome) SELECT DISTINCT nome_estado FROM cidade; 
ALTER TABLE cidade ADD COLUMN estado_id bigint NOT NULL;
UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);  

ALTER TABLE cidade ADD CONSTRAINT fk_cidade__estado FOREIGN KEY (estado_id) REFERENCES estado (id);
ALTER TABLE cidade DROP COLUMN nome_estado;
ALTER TABLE cidade CHANGE nome_cidade nome varchar(80) NOT NULL; 
