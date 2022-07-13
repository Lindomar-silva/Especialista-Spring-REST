
    create table cidade (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        estado_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table cozinha (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table estado (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table forma_pagamento (
       id bigint not null auto_increment,
        descricao varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table grupo (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table grupo_permissao (
       grupo_id bigint not null,
        permissao_id bigint not null
    ) engine=InnoDB;

    create table permissao (
       id bigint not null auto_increment,
        descricao varchar(255) not null,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table produto (
       id bigint not null auto_increment,
        ativo bit not null,
        descricao varchar(255) not null,
        nome varchar(255) not null,
        preco decimal(19,2) not null,
        restaurante_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table restaurante (
       id bigint not null auto_increment,
        data_atualizacao datetime not null,
        data_cadastro datetime not null,
        endereco_bairro varchar(255),
        endereco_cep varchar(255),
        endereco_complemento varchar(255),
        endereco_logradouro varchar(255),
        endereco_numero varchar(255),
        nome varchar(255) not null,
        taxa_frete decimal(19,2) not null,
        cozinha_id bigint not null,
        endereco_cidade_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table restaurante_forma_pagamento (
       restaurante_id bigint not null,
        forma_pagamento_id bigint not null
    ) engine=InnoDB;

    create table usuario (
       id bigint not null auto_increment,
        da0ta_cadastro datetime not null,
        email varchar(255) not null,
        nome varchar(255) not null,
        senha varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table usuario_grupo (
       usuario_id bigint not null,
        grupo_id bigint not null
    ) engine=InnoDB;

    alter table cidade 
       add constraint FKkworrwk40xj58kevvh3evi500 
       foreign key (estado_id) 
       references estado (id);

    alter table grupo_permissao 
       add constraint FKh21kiw0y0hxg6birmdf2ef6vy 
       foreign key (permissao_id) 
       references permissao (id);

    alter table grupo_permissao 
       add constraint FKta4si8vh3f4jo3bsslvkscc2m 
       foreign key (grupo_id) 
       references grupo (id);

    alter table produto 
       add constraint FKb9jhjyghjcn25guim7q4pt8qx 
       foreign key (restaurante_id) 
       references restaurante (id);

    alter table restaurante 
       add constraint FK76grk4roudh659skcgbnanthi 
       foreign key (cozinha_id) 
       references cozinha (id);

    alter table restaurante 
       add constraint FKbc0tm7hnvc96d8e7e2ulb05yw 
       foreign key (endereco_cidade_id) 
       references cidade (id);

    alter table restaurante_forma_pagamento 
       add constraint FK7aln770m80358y4olr03hyhh2 
       foreign key (forma_pagamento_id) 
       references forma_pagamento (id);

    alter table restaurante_forma_pagamento 
       add constraint FKa30vowfejemkw7whjvr8pryvj 
       foreign key (restaurante_id) 
       references restaurante (id);

    alter table usuario_grupo 
       add constraint FKk30suuy31cq5u36m9am4om9ju 
       foreign key (grupo_id) 
       references grupo (id);

    alter table usuario_grupo 
       add constraint FKdofo9es0esuiahyw2q467crxw 
       foreign key (usuario_id) 
       references usuario (id);
INSERT INTO cozinha (nome) VALUES 
('Tailandesa'), ('Indiana'), ('Brasileira'), ('Argentina');
INSERT INTO estado (id, nome) VALUES 
(1, 'São Paulo'), (2, 'Minas Gerais'), (3, 'Paraná');
INSERT cidade (nome, estado_id) VALUES 
('Americana', 1), ('Santa Barabara d''Oeste', 1), ('Urberlandia', 2), ('Belo Horizonte', 2), ('Londrina', 3), ('Ivaiporâ', 3);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) VALUES
('Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', UTC_TIMESTAMP, UTC_TIMESTAMP),
('Thai Delivery', 9.50, 1, 2, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP),
('Tuk Tuk Comida Indiana', 15.00, 2, 3, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP),
('Panela de Ferro', 0, 3, 4, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP),
('Fogão a Lemha', 0, 3, 2, '13588-000', 'Rua 13 de Maio', '200', 'Jardim 2', UTC_TIMESTAMP, UTC_TIMESTAMP),
('Java Steakhouse', 12, 3, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP),
('Lanchonete do Tio Sam', 11, 4, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP),
('Bar da Maria', 6, 4, NULL, NULL, NULL, NULL, NULL, UTC_TIMESTAMP, UTC_TIMESTAMP);
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
