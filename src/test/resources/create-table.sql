create schema if not exists products;

create table if not exists products.colors (
	id varchar(100) not null,
	description varchar(300) not null,
	short_description varchar(30) not null,
	primary key (id)
);
