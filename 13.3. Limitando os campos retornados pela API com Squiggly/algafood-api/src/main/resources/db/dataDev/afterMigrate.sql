SET foreign_key_checks = 0;

TRUNCATE cidade;
TRUNCATE cozinha;
TRUNCATE estado;
TRUNCATE forma_pagamento;
TRUNCATE grupo;
TRUNCATE grupo_permissao;
TRUNCATE permissao;
TRUNCATE produto;
TRUNCATE restaurante;
TRUNCATE restaurante_forma_pagamento;
TRUNCATE usuario;
TRUNCATE usuario_grupo;
TRUNCATE restaurante_usuario_responsavel;
TRUNCATE item_pedido; 
TRUNCATE pedido; 

SET foreign_key_checks = 1;

INSERT INTO cozinha (nome) VALUES 
('Tailandesa'), ('Indiana'), ('Brasileira'), ('Argentina');

INSERT INTO estado (nome) VALUES ('São Paulo'), ('Minas Gerais'), ('Paraná');

INSERT cidade (nome, estado_id) VALUES 
('Americana', 1), ('Santa Barabara d''Oeste', 1), ('Urberlandia', 2), ('Belo Horizonte', 2), ('Londrina', 3), ('Ivaiporâ', 3);

INSERT INTO restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo, aberto) VALUES
('Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Thai Delivery', 9.50, 1, 2, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Tuk Tuk Comida Indiana', 15.00, 2, 3, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Panela de Ferro', 0, 3, 4, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Fogão a Lemha', 0, 3, 2, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Java Steakhouse', 12, 3, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Lanchonete do Tio Sam', 11, 4, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE),
('Bar da Maria', 6, 4, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE, TRUE);

INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES 
('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1),
('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1),
('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2),
('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3),
('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3),
('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4),
('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4),
('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5),
('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

INSERT INTO forma_pagamento (descricao) VALUES
('Cartão de crédito'), ('Cartão de débito'), ('Dinheiro');

INSERT INTO permissao (nome, descricao) VALUES
('CONSULTAR_COZINHAS', 'Permite consultar Cozinhas'), ('EDITAR_COZINHAS', 'Permite editar Cozinhas');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES 
(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO grupo (nome) VALUES ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

INSERT INTO usuario (nome, email, senha, data_cadastro) VALUES
('João da Silva', 'joao.ger@algafood.com', '123', UTC_TIMESTAMP),
('Maria Joaquina', 'maria.vnd@algafood.com', '123', UTC_TIMESTAMP),
('José Souza', 'jose.aux@algafood.com', '123', UTC_TIMESTAMP),
('Sebastião Martins', 'sebastiao.cad@algafood.com', '123', UTC_TIMESTAMP),
('Manoel Lima', 'manoel.loja@gmail.com', '123', UTC_TIMESTAMP);
 
INSERT INTO grupo_permissao VALUES (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

INSERT INTO usuario_grupo VALUES (1, 1), (1, 2), (2, 3), (2, 4), (3, 4);

INSERT INTO restaurante_usuario_responsavel VALUES (1, 3), (1, 4), (2, 1), (2, 3), (3, 4), (3, 5); 

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES ('c08f9902-47fe-4798-a2ee-41f2cbf44edd', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CRIADO', UTC_TIMESTAMP, 298.90, 10, 308.90);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (1, 1, 1, 78.9, 78.9, NULL),
(1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES ('f9a7faaf-c0ca-4b32-8a70-03eae64b4ee0', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CRIADO', UTC_TIMESTAMP, 79, 0, 79);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (2, 6, 1, 79, 79, 'Ao ponto');

