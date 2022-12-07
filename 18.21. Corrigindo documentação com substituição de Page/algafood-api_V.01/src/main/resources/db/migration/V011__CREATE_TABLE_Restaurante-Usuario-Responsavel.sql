CREATE TABLE restaurante_usuario_responsavel (
	usuario_id bigint NOT NULL,
	restaurante_id bigint NOT NULL,
	PRIMARY KEY (restaurante_id, usuario_id),
	CONSTRAINT fk_restaurante_usuario_responsavel__usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
	CONSTRAINT fk_restaurante_usuario_responsavel__restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;