INSERT INTO tb_cargo (authority) VALUES ('ROLE_ADM');
INSERT INTO tb_cargo (authority) VALUES ('ROLE_OPERATOR');

INSERT INTO tb_categoria (nome_categoria) VALUES ('Remédio');
INSERT INTO tb_categoria (nome_categoria) VALUES ('Ração');
INSERT INTO tb_categoria (nome_categoria) VALUES ('Brinquedo');

INSERT INTO tb_plano (plano, preco, descricao, nome) VALUES (0, 49.90, 'Cobertura básica para consultas veterinárias e vacinas.', 'Plano Básico');

INSERT INTO tb_plano (plano, preco, descricao, nome) VALUES (1, 99.90, 'Cobertura essencial para consultas veterinárias de rotina e vacinas. Inclui uma consulta de emergência por ano. Desconto de 20% em procedimentos odontológicos para pets. Acesso a um check-up anual completo para seu pet, incluindo exames de sangue e fezes. Suporte telefônico 24 horas para tirar dúvidas sobre a saúde e bem-estar do seu pet. Cobertura de vermifugação e antipulgas duas vezes ao ano.', 'Plano Essencial');

INSERT INTO tb_plano (plano, preco, descricao, nome) VALUES (2, 149.90, 'Cobertura completa para consultas veterinárias de rotina, vacinas, exames, e cirurgias. Consultas de emergência ilimitadas. Desconto de 50% em procedimentos odontológicos e cirurgias para pets. Cobertura total de exames de diagnóstico, incluindo raio-X, ultrassom, e exames laboratoriais. Acesso a especialistas veterinários, como cardiologistas e dermatologistas. Internações cobertas por até 5 dias por ano. Suporte telefônico 24 horas e atendimento domiciliar para emergências. Cobertura para tratamentos fisioterápicos e acupuntura.', 'Plano Completo');

INSERT INTO tb_usuario (id, cpf_cnpj, email, nome, senha) VALUES (UUID(), '88.569.897/0001-10', 'adm@gmail.com', 'José', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_usuario (id, cpf_cnpj, email, nome, senha) VALUES (UUID(), '19.025.934/0001-06', 'jose@gmail.com', 'Augusto', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_carrinho (id) VALUES ('7a4a3eca-f327-4f77-8575-bb7cc1972652');

UPDATE tb_usuario SET carrinho_id = '7a4a3eca-f327-4f77-8575-bb7cc1972652' WHERE email = 'adm@gmail.com';

INSERT INTO tb_carrinho (id) VALUES ('f29d3780-079e-41c5-bb3d-7765bfc7cf71');

UPDATE tb_usuario SET carrinho_id = 'f29d3780-079e-41c5-bb3d-7765bfc7cf71' WHERE email = 'jose@gmail.com';

INSERT INTO tb_usuario_cargo (usuario_id, cargo_id) SELECT id, 1 FROM tb_usuario WHERE email = 'adm@gmail.com';

INSERT INTO tb_usuario_cargo (usuario_id, cargo_id) SELECT id, 2 FROM tb_usuario WHERE email = 'jose@gmail.com';