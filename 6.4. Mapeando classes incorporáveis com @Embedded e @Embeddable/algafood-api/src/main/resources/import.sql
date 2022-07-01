INSERT INTO cozinha (id, nome) VALUES 
(1, 'Tailandesa'), (2, 'Indiana'), (3, 'Brasileira');

INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES 
('Thai Gourmet', 10, 1), ('Thai Delivery', 9.50, 1), ('Tuk Tuk Comida Indiana', 15.00, 2), ('Panela de Ferro', 0, 3) ,('Fogão a Lemha', 0, 3);

INSERT INTO forma_pagamento (descricao) VALUES
('Cartão de crédito'), ('Cartão de débito'), ('Dinheiro');

INSERT INTO permissao (nome, descricao) VALUES
('Admin', 'Permissão full para administrador do sistema'), ('Consultar produto', 'Permite somente consulta de produtos');

INSERT INTO estado (id, nome) VALUES 
(1, 'São Paulo'), (2, 'Minas Gerais'), (3, 'Paraná');

INSERT cidade (nome, estado_id) VALUES 
('Americana', 1), ('Santa Barabara d''Oeste', 1), ('Urberlandia', 2), ('Belo Horizonte', 2), ('Londrina', 3), ('Ivaiporâ', 3);

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES 
(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);
