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

SET foreign_key_checks = 1;

INSERT INTO cozinha (nome) VALUES 
('Tailandesa'), ('Indiana'), ('Brasileira'), ('Argentina');

INSERT INTO estado (id, nome) VALUES 
(1, 'São Paulo'), (2, 'Minas Gerais'), (3, 'Paraná');

INSERT cidade (nome, estado_id) VALUES 
('Americana', 1), ('Santa Barabara d''Oeste', 1), ('Urberlandia', 2), ('Belo Horizonte', 2), ('Londrina', 3), ('Ivaiporâ', 3);

INSERT INTO restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao, ativo) VALUES
('Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Thai Delivery', 9.50, 1, 2, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Tuk Tuk Comida Indiana', 15.00, 2, 3, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Panela de Ferro', 0, 3, 4, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Fogão a Lemha', 0, 3, 2, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Java Steakhouse', 12, 3, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Lanchonete do Tio Sam', 11, 4, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE),
('Bar da Maria', 6, 4, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP, TRUE);

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
('Admin', 'Permissão full para administrador do sistema'), ('Consultar produto', 'Permite somente consulta de produtos');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES 
(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);