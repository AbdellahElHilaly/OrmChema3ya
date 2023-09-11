drop database if exists jdbc_posts_java;
create database jdbc_posts_java;
use jdbc_posts_java;

create table posts (
  id int not null auto_increment,
  title varchar(255) not null,
  content text not null,
  primary key (id)
);

insert into posts (title, content) values ('title1', 'content1');
insert into posts (title, content) values ('title2', 'content2');
insert into posts (title, content) values ('title3', 'content3');
insert into posts (title, content) values ('title4', 'content4');


