use products;

CREATE DATABASE `products` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

create table products.colors (
	id varchar(100) not null,
	description varchar(300) not null,
	short_description varchar(30) not null,
	primary key (id)
);

