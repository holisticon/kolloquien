CREATE DATABASE  IF NOT EXISTS `forum`;
USE `forum`;

DROP TABLE IF EXISTS `forum_entry`;

create table forum_entry(
fen_id BIGINT AUTO_INCREMENT NOT NULL UNIQUE,
fen_contributor varchar(256),
fen_title varchar(256),
fen_creation_date timestamp not null,
fen_state enum('NEW', 'ACCEPTED', 'REJECTED') not null,
fen_token varchar(64) not null,
fen_check_date timestamp ,
primary key(fen_id)
);

select * from forum.forum_entry;