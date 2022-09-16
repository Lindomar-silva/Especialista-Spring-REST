CREATE TABLE forma_pagamento (
	id bigint NOT NULL auto_increment,
	descricao varchar(60) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE grupo (
	id bigint NOT NULL auto_increment,
	nome varchar(60) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE grupo_permissao (
	grupo_id bigint NOT NULL,
	permissao_id bigint NOT NULL,
	PRIMARY KEY (grupo_id, permissao_id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE permissao (
	id bigint NOT NULL auto_increment,
	nome varchar(100) NOT NULL,
	descricao varchar(60) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE produto (
	id bigint NOT NULL auto_increment,
	restaurante_id bigint NOT NULL,
	nome varchar(80) NOT NULL,
	descricao text NOT NULL,
	preco decimal(10,2) NOT NULL,
	ativo tinyint(1) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE restaurante (
	id bigint NOT NULL auto_increment,
	cozinha_id bigint NOT NULL,
	nome varchar(80) NOT NULL,
	taxa_frete decimal(10,2) NOT NULL,
	data_atualizacao datetime NOT NULL,
	data_cadastro datetime NOT NULL,
	endereco_cidade_id bigint,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),
	PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE restaurante_forma_pagamento (
	restaurante_id bigint NOT NULL,
	forma_pagamento_id bigint NOT NULL,
	PRIMARY KEY (restaurante_id, forma_pagamento_id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE usuario (
	id bigint NOT NULL auto_increment,
	nome varchar(80) NOT NULL,
	email varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	data_cadastro datetime NOT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE usuario_grupo (
	usuario_id bigint NOT NULL,
	grupo_id bigint NOT NULL,
	PRIMARY KEY (usuario_id, grupo_id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;


ALTER TABLE grupo_permissao ADD CONSTRAINT fk_grupo_permissao_permissao FOREIGN KEY (permissao_id) REFERENCES permissao (id);
ALTER TABLE grupo_permissao ADD CONSTRAINT fk_grupo_permissao_grupo FOREIGN KEY (grupo_id) REFERENCES grupo (id);
ALTER TABLE produto ADD CONSTRAINT fk_produto_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);
ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cozinha FOREIGN KEY (cozinha_id) REFERENCES cozinha (id);
ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id);
ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_rest_forma_pagto_forma_pagto FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);
ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_rest_forma_pagto_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);
ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_grupo FOREIGN KEY (grupo_id) REFERENCES grupo (id);
ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id);