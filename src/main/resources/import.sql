insert into cozinha (id,nome) VALUES (1, 'Tailandesa');
insert into cozinha (id,nome) VALUES (2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) VALUES('Tailandia Food',9.50,1);
insert into restaurante (nome, taxa_frete, cozinha_id) VALUES('Indiano',4.30,2);

insert into forma_pagamento (descricao) VALUES ('Dinheiro');
insert into forma_pagamento (descricao) VALUES ('Débito');
insert into forma_pagamento (descricao) VALUES ('Crédito');
insert into forma_pagamento (descricao) VALUES ('VR');
insert into forma_pagamento (descricao) VALUES ('VA');

insert into permissao (nome, descricao) VALUES ('Consultar','Pode consultar');
insert into permissao (nome, descricao) VALUES ('Excluir','Pode excluir');
insert into permissao (nome) VALUES ('Gravar');

insert into estado (id, nome) VALUES (1, 'São Paulo');
insert into estado (id, nome) VALUES (2, 'Rio de Janeiro');
insert into estado (id, nome) VALUES (3, 'Minas Gerais');

insert into cidade (nome, estado_id) VALUES ('São Paulo',1);
insert into cidade (nome, estado_id) VALUES ('Campinas',1);
insert into cidade (nome, estado_id) VALUES ('Rio de Janeiro',2);
insert into cidade (nome, estado_id) VALUES ('Niterói',2);
insert into cidade (nome, estado_id) VALUES ('Ouro Preto',3);
insert into cidade (nome, estado_id) VALUES ('Tiradentes',3);
