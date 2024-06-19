INSERT INTO tb_usuario (id, email, nome, senha)
VALUES (random_uuid(), 'jinBRGame153@gmail.com', 'José', '$2a$10$E9V4XzU0S7YxMFiY.v4YvO/8GvV5lY5NHlm3YgbyYz/J6CRp2u5cK');

INSERT INTO tb_usuario_cargo (usuario_id, cargo_id)
SELECT id, 1 FROM tb_usuario WHERE email = 'jinBRGame153@gmail.com';