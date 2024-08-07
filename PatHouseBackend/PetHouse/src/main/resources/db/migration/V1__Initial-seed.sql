create table tb_cargo (id bigint generated by default as identity, authority varchar(255) not null, primary key (id));
create table tb_carrinho (id uuid not null, primary key (id));
create table tb_categoria (id bigint generated by default as identity, nome_categoria varchar(255) not null, primary key (id));
create table tb_pedido (data date not null, carrinho_id uuid, id uuid not null, usuario_id uuid, primary key (id));
create table tb_pedido_produto (produto_id bigint not null, pedido_id uuid not null);
create table tb_plano (plano tinyint not null check (plano between 0 and 2), preco numeric(38,2) not null, id bigint generated by default as identity, descricao varchar(255) not null, nome varchar(255) not null, primary key (id));
create table tb_produto (preco numeric(38,2) not null, tipo tinyint not null check (tipo between 0 and 1), categoria_id bigint, id bigint generated by default as identity, descricao TEXT not null, img TEXT, nome varchar(255) not null, primary key (id));
create table tb_usuario (plano_id bigint unique, carrinho_id uuid unique, id uuid not null, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id));
create table tb_usuario_cargo (cargo_id bigint not null, usuario_id uuid not null, primary key (cargo_id, usuario_id));
alter table if exists tb_pedido add constraint FKgch7ub6vvk98jibobyd95isri foreign key (carrinho_id) references tb_carrinho;
alter table if exists tb_pedido add constraint FK5h0d3j6fio8o9dmrdwd1n58k1 foreign key (usuario_id) references tb_usuario;
alter table if exists tb_pedido_produto add constraint FKc3xekt4kw8on58e9ieyqiv2ce foreign key (produto_id) references tb_produto;
alter table if exists tb_pedido_produto add constraint FKjw6uewldiincmxg0ehs09asu foreign key (pedido_id) references tb_pedido;
alter table if exists tb_produto add constraint FK1ksfe2oumgjxke3oc5op41lej foreign key (categoria_id) references tb_categoria;
alter table if exists tb_usuario add constraint FK4a6uire4wxu3xt1yh7km3o377 foreign key (carrinho_id) references tb_carrinho;
alter table if exists tb_usuario add constraint FKgx3figo8bmyutg0cy6vustook foreign key (plano_id) references tb_plano;
alter table if exists tb_usuario_cargo add constraint FKfoxfmrp32u59p9yvguydp9ybj foreign key (cargo_id) references tb_cargo;
alter table if exists tb_usuario_cargo add constraint FKdaew2172j172duhsiyma5rfq5 foreign key (usuario_id) references tb_usuario;

ALTER TABLE tb_plano ALTER COLUMN descricao SET DATA TYPE TEXT;

CREATE TABLE tb_carrinho_produto (
    carrinho_id UUID NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    preco numeric(38,2),
    nome VARCHAR(255),
    img TEXT,
    PRIMARY KEY (carrinho_id, produto_id),
    FOREIGN KEY (carrinho_id) REFERENCES tb_carrinho(id),
    FOREIGN KEY (produto_id) REFERENCES tb_produto(id)
);