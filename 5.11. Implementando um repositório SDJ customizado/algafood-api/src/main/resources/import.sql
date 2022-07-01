INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');

INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Comida Cazeira', 22.30, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Panela de Ferro', 20.50, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Restarante Vó Maria', 19.50, 2);

INSERT INTO forma_pagamento (descricao) VALUES ('A vista');
INSERT INTO forma_pagamento (descricao) VALUES ('A prazo');

INSERT INTO permissao (nome, descricao) VALUES ('Admin', 'Permissão full para administrador do sistema');
INSERT INTO permissao (nome, descricao) VALUES ('Consultar produto', 'Permite somente consulta de produtos');

INSERT INTO estado (id, nome) VALUES (1, 'São Paulo');
INSERT INTO estado (id, nome) VALUES (2, 'Minas Gerais');
INSERT INTO estado (id, nome) VALUES (3, 'Paraná');

INSERT cidade (nome, estado_id) VALUES ('Americana', 1);
INSERT cidade (nome, estado_id) VALUES ('Santa Barabara d''Oeste', 1);
INSERT cidade (nome, estado_id) VALUES ('Urberlandia', 2);
INSERT cidade (nome, estado_id) VALUES ('Belo Horizonte', 2);
INSERT cidade (nome, estado_id) VALUES ('Londrina', 3);
INSERT cidade (nome, estado_id) VALUES ('Ivaiporâ', 3);