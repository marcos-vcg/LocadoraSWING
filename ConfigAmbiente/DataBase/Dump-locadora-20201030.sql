create database if not exists locadora
	default character set utf8mb4
	default collate utf8mb4_general_ci;
    
use locadora;

create table if not exists genero(
	id int not null auto_increment,
	nome varchar(20) not null,
    primary key(id)
) default charset utf8mb4;

drop table genero;

insert into genero (nome) values ('Ação');
insert into genero (nome) values ('Aventura');
insert into genero (nome) values ('Comédia');
insert into genero (nome) values ('Drama');
insert into genero (nome) values ('Romance');
insert into genero (nome) values ('Suspense');
insert into genero (nome) values ('Terror');
insert into genero (nome) values ('Musical');
insert into genero (nome) values ('Retrô');
insert into genero (nome) values ('Infantil');

select * from genero;

create table if not exists categoria(
	id int not null auto_increment,
	nome varchar(20) not null,
    preco varchar(20) not null,
    primary key(id)
) default charset utf8mb4;

insert into categoria (nome, preco) values ('Lançamento', '10,00');
insert into categoria (nome, preco) values ('Atual', '7,50');
insert into categoria (nome, preco) values ('Intermediário', '5,00');
insert into categoria (nome, preco) values ('Antigo', '2,50');

select * from categoria;
SELECT * FROM categoria WHERE id = '4';

create table if not exists filme(
	id int not null auto_increment,
	titulo varchar(20) not null,
    genero int not null,
    copias int not null,
    sinopse varchar(200),
    duracao varchar(20),
    lancamento varchar(20),
    imagem  BLOB,
    categoria int,
    primary key(id),
    foreign key(genero) references genero(id),
    foreign key(categoria) references categoria(id)
) default charset utf8mb4;


select * from filme;
SELECT * FROM filme ORDER BY titulo;
delete from filme where id = '1';
update filme set lancamento = '2020-02-04' where id = '1';
update filme set imagem = null where id = '3';












create table if not exists cliente(
	id int not null auto_increment,
	nome varchar(40) not null,
    cpf varchar(15),
    telefone varchar(15),
    email varchar(20),
    nascimento varchar(15),
    endereco varchar(40),
    imagem blob,
	primary key(id)
	) default charset utf8mb4;
    
select * from cliente;
    
    
create table if not exists dependente(
	id int not null auto_increment,
	nome varchar(20) not null,
    grau enum('AVO', 'PAI', 'FILHO', 'TIO', 'SOBRINHO', 'CONJUJE') not null,
    titular int not null,
    primary key(id),
    foreign key (titular) references cliente(id)
) default charset utf8mb4;
    
    
    
    
create table if not exists locacao(
	id int not null auto_increment,
	filme int not null,
	aluguel date,
	devolucao date,
	primary key(id),
	foreign key(filme) references filme(id)
) default charset utf8mb4;