SET foreign_key_checks = 0;

#-- O primeiro container vai bloquear as tabelas para o segundo conatainer nao executar (Somente em desenvolvimento)
LOCK TABLES cidade WRITE, cozinha WRITE, estado WRITE, forma_pagamento WRITE,
	grupo WRITE, grupo_permissao WRITE, permissao WRITE,
	produto WRITE, restaurante WRITE, restaurante_forma_pagamento WRITE,
	restaurante_usuario_responsavel WRITE, usuario WRITE, usuario_grupo WRITE,
	pedido WRITE, item_pedido WRITE, foto_produto WRITE, oauth2_registered_client WRITE;

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
TRUNCATE foto_produto;
TRUNCATE oauth2_registered_client;

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
('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1),
('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1),
('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2),
('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3),
('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3),
('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4),
('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4),
('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5),
('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

INSERT INTO forma_pagamento (descricao, data_atualizacao) VALUES
('Cartão de crédito', UTC_TIMESTAMP), ('Cartão de débito', UTC_TIMESTAMP), ('Dinheiro', UTC_TIMESTAMP);

# INSERT INTO permissao (nome, descricao) VALUES
# ('CONSULTAR_COZINHAS', 'Permite consultar Cozinhas'), ('EDITAR_COZINHAS', 'Permite editar Cozinhas'),
# ('CONSULTAR_FORMAS_PAGAMENTO', 'Permite consultar formas de pagamento'), ('EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento'),
# ('CONSULTAR_CIDADES', 'Permite consultar cidades'), ('EDITAR_CIDADES', 'Permite criar ou editar cidades'),
# ('CONSULTAR_ESTADOS', 'Permite consultar estados'), ('EDITAR_ESTADOS', 'Permite criar ou editar estados'),
# ('CONSULTAR_USUARIOS', 'Permite consultar usuários'), ('EDITAR_USUARIOS', 'Permite criar ou editar usuários'),
# ('CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes'), ('EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes'),
# ('CONSULTAR_PRODUTOS', 'Permite consultar produtos'), ('EDITAR_PRODUTOS', 'Permite criar ou editar produtos'),
# ('CONSULTAR_PEDIDOS', 'Permite consultar pedidos'), ('GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos'),
# ('GERAR_RELATORIOS', 'Permite gerar relatórios');

INSERT INTO permissao (nome, descricao) VALUES
('EDITAR_COZINHAS', 'Permite editar Cozinhas'),
('EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento'),
('EDITAR_CIDADES', 'Permite criar ou editar cidades'),
('EDITAR_ESTADOS', 'Permite criar ou editar estados'),
('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários, grupos e permissões'), 
('EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários, grupos e permissões'),
('EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes'),
('CONSULTAR_PEDIDOS', 'Permite consultar pedidos'), 
('GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos'),
('GERAR_RELATORIOS', 'Permite gerar relatórios');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES 
(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO grupo (nome) VALUES ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

# --Adiciona todas as permissoes no grupo do gerente
INSERT INTO grupo_permissao (grupo_id, permissao_id)
SELECT 1, id FROM permissao;

# --Adiciona permissoes no grupo do vendedor
INSERT INTO grupo_permissao (grupo_id, permissao_id)
SELECT 2, id FROM permissao WHERE nome LIKE 'CONSULTAR_%';

INSERT INTO grupo_permissao (grupo_id, permissao_id)
SELECT 2, id FROM permissao WHERE nome = 'EDITAR_RESTAURANTES';

# --Adiciona permissoes no grupo do auxiliar
INSERT INTO grupo_permissao (grupo_id, permissao_id)
SELECT 3, id FROM permissao WHERE nome LIKE 'CONSULTAR_%';

# --Adiciona permissoes no grupo cadastrador
INSERT INTO grupo_permissao (grupo_id, permissao_id)
SELECT 4, id FROM permissao WHERE nome LIKE '%_RESTAURANTES';

INSERT INTO usuario (nome, email, senha, data_cadastro) VALUES
('João da Silva', 'joao.ger@algafood.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP),
('Maria Joaquina', 'maria.vnd@algafood.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP),
('José Souza', 'jose.aux@algafood.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP),
('Sebastião Martins', 'sebastiao.cad@algafood.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP),
('Manoel Lima', 'manoel.loja@gmail.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP),
('Débora Mendonça', 'algamony.lss+debora@gmail.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP),
('Carlos Lima', 'algamony.lss+carlos@gmail.com', '$2a$12$1mlYwtdJDj41eosQB1vZ1O1Ug64Z9g9vDZ9yz57xFuT463vjeGFia', UTC_TIMESTAMP);

INSERT INTO usuario_grupo VALUES (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

INSERT INTO restaurante_usuario_responsavel VALUES (5, 1), (5, 3); 

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES ('c08f9902-47fe-4798-a2ee-41f2cbf44edd', 1, 6, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CRIADO', UTC_TIMESTAMP, 298.90, 10, 308.90);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (1, 1, 1, 78.9, 78.9, NULL),
(1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES ('f9a7faaf-c0ca-4b32-8a70-03eae64b4ee0', 4, 6, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CRIADO', UTC_TIMESTAMP, 79, 0, 79);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (2, 6, 1, 79, 79, 'Ao ponto');

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
VALUES ('b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 7, 1, 1, '38400-222', 'Rua Natal', '200', NULL, 'Brasil',
        'ENTREGUE', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (3, 2, 1, 110, 110, NULL);

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
VALUES ('5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 7, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'ENTREGUE', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (4, 3, 2, 87.2, 174.4, NULL);

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
VALUES ('8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'ENTREGUE', '2019-11-03 02:00:30', '2019-11-03 02:01:21', '2019-11-03 02:20:10', 87.2, 10, 97.2);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (5, 3, 1, 87.2, 87.2, NULL);

INSERT INTO pedido (uuid_cod, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES ('d08f9902-47fe-4798-a2ee-41f2cbf44edd', 1, 4, 3, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CRIADO', UTC_TIMESTAMP, 298.90, 10, 308.90);

INSERT INTO item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao) 
VALUES (6, 3, 1, 78.9, 78.9, NULL),
(6, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO oauth2_registered_client (
	id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, 
	client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings
)
VALUES (
	'1', 'algafood-backend', '2024-10-10 13:59:55', '$2a$10$M3t.cb5lAZRAhnzmXYRNcuEQ1bHgLQ6xEqg233OOs4iBqCE0b90Ki', NULL, '1', 'client_secret_basic', 'client_credentials', '', 'READ', 
	'{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}'
);

INSERT INTO oauth2_registered_client (
	id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, 
	client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings
)
VALUES (
	'2', 'algafood-web', '2024-10-10 13:59:55', '$2a$10$EOY2TTr4s4HcbIrCjg9ysu5syYPL8T/QL5S3b4Bfr/SUnql073rPG', NULL, '2', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', 'READ,WRITE', 
	'{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000]}'
);

INSERT INTO oauth2_registered_client (
	id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, 
	client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings
)
VALUES (
	'3', 'foodanalytics', '2024-10-10 13:59:55', '$2a$10$8U/5agrMF0GSomONGmQcdu5JRfqdpILb648lMtVyvqZv8IYD/76Ha', NULL, '3', 'client_secret_basic', 'authorization_code', 'http://www.foodanalytics.local:8082', 'READ,WRITE', 
	'{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}'
);

UNLOCK TABLES;
