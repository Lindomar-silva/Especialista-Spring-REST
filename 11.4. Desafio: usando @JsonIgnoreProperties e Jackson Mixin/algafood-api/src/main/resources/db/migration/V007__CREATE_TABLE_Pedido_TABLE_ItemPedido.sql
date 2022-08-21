CREATE TABLE pedido(
	id BIGINT NOT NULL AUTO_INCREMENT,
	subtotal DECIMAL(10,2) NOT NULL,
	taxa_frete DECIMAL(10,2) NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	data_criacao DATETIME NOT NULL,
	data_confirmacao DATETIME NULL,
	data_Cancelamento DATETIME NULL,
	data_Entrega DATETIME NULL,
  	status VARCHAR(10) NOT NULL,
	
	endereco_cidade_id BIGINT(20) NOT NULL,
  	endereco_cep VARCHAR(9) NOT NULL,
  	endereco_logradouro VARCHAR(100) NOT NULL,
  	endereco_numero VARCHAR(20) NOT NULL,
 	endereco_complemento VARCHAR(60),
  	endereco_bairro VARCHAR(60) NOT NULL,
  	
  	usuario_cliente_id BIGINT NOT NULL,
  	restaurante_id BIGINT NOT NULL,
  	forma_pagamento_id BIGINT NOT NULL,
	
	CONSTRAINT pk_pedido PRIMARY KEY(id),
	CONSTRAINT fk_pedido__endereco_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id),
	CONSTRAINT fk_pedido__usuario_cliente FOREIGN KEY (usuario_cliente_id) REFERENCES usuario (id),
	CONSTRAINT fk_pedido__restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
	CONSTRAINT fk_pedido__forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
)ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

CREATE TABLE item_pedido(
	id BIGINT NOT NULL AUTO_INCREMENT,
	pedido_id BIGINT NOT NULL,
	produto_id BIGINT NOT NULL,
	quantidade SMALLINT(6) NOT NULL,
	preco_unitario DECIMAL(10,2) NOT NULL,
	preco_total DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(10),
  	
	CONSTRAINT pk_item_pedido PRIMARY KEY(id),
	UNIQUE KEY uk_item_pedido__produto (pedido_id, produto_id),
	CONSTRAINT fk_item_pedido__pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id),
	CONSTRAINT fk_item_pedido__produto FOREIGN KEY (produto_id) REFERENCES produto (id)
)ENGINE = InnoDB DEFAULT CHARSET = UTF8MB4;

