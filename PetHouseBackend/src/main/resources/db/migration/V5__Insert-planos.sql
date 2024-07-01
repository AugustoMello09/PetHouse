ALTER TABLE tb_plano ALTER COLUMN descricao SET DATA TYPE TEXT;

INSERT INTO tb_plano (nome, preco, descricao, plano) VALUES 
('Plano Básico', 49.90, 'Cobertura básica para consultas veterinárias e vacinas.', 0);

INSERT INTO tb_plano (nome, preco, descricao, plano) VALUES 
('Plano Essencial', 99.90, 'Cobertura essencial para consultas veterinárias de rotina e vacinas. Inclui uma consulta de emergência por ano. Desconto de 20% em procedimentos odontológicos para pets. Acesso a um check-up anual completo para seu pet, incluindo exames de sangue e fezes. Suporte telefônico 24 horas para tirar dúvidas sobre a saúde e bem-estar do seu pet. Cobertura de vermifugação e antipulgas duas vezes ao ano.', 1);

INSERT INTO tb_plano (nome, preco, descricao, plano) VALUES 
('Plano Completo', 149.90, 'Cobertura completa para consultas veterinárias de rotina, vacinas, exames, e cirurgias. Consultas de emergência ilimitadas. Desconto de 50% em procedimentos odontológicos e cirurgias para pets. Cobertura total de exames de diagnóstico, incluindo raio-X, ultrassom, e exames laboratoriais. Acesso a especialistas veterinários, como cardiologistas e dermatologistas. Internações cobertas por até 5 dias por ano. Suporte telefônico 24 horas e atendimento domiciliar para emergências. Cobertura para tratamentos fisioterápicos e acupuntura.', 2);