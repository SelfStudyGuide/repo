-- Drop tables
alter table HOMEWORK drop constraint HOMEWORK_FK_STUDENT;
alter table TOPIC drop constraint TOPIC_FK_MOD;
alter table HOMEWORK_MODULE drop constraint HOMEWORK_MODULE_FK_HW;
alter table HOMEWORK_MODULE drop constraint HOMEWORK_MODULE_FK_MOD;
alter table TOPIC_PROGRESS drop constraint TOPIC_PROGRESS_FK_HW;
alter table TOPIC_PROGRESS drop constraint TOPIC_PROGRESS_FK_TOPIC;

drop table TOPIC_PROGRESS if exists
drop table HOMEWORK if exists;
drop table STUDENT if exists;
drop table TOPIC if exists;
drop table MODULE if exists;
drop table HOMEWORK_MODULE if exists;
drop table authorities if exists;
drop table users if exists;
drop table persistent_logins if exists;
drop table group_members if exists;
drop table group_authorities if exists;
drop table groups if exists;

-- Create tables

------------
-- MODULE --
------------
create table MODULE (
ID integer generated by default as identity (start with 1), 
DESC varchar(255), 
NAME varchar(255) not null, 
primary key (ID), 
unique (NAME));

------------
-- STUDENT --
------------ 
create table STUDENT (
ID integer generated by default as identity (start with 1), 
NAME varchar(255) not null, 
EMAIL varchar(255), 
primary key (ID));


------------
-- HOMEWORK --
------------ 
create table HOMEWORK (
ID integer generated by default as identity (start with 1), 
STUD_ID integer, primary key (ID));

alter table HOMEWORK add constraint HOMEWORK_FK_STUDENT foreign key (STUD_ID) references STUDENT;

------------
-- TOPIC --
------------ 
create table TOPIC (
ID integer generated by default as identity (start with 1), 
DESC varchar(255), 
NAME varchar(255) not null, 
MOD_ID integer, 
primary key (ID));

alter table TOPIC add constraint TOPIC_FK_MOD foreign key (MOD_ID) references MODULE;

------------
-- HOMEWORK_MODULE --
------------ 
create table HOMEWORK_MODULE (
HW_ID integer not null, 
MODULE_ID integer not null);

alter table HOMEWORK_MODULE add constraint HOMEWORK_MODULE_FK_HW foreign key (HW_ID) references HOMEWORK;
alter table HOMEWORK_MODULE add constraint HOMEWORK_MODULE_FK_MOD foreign key (MODULE_ID) references MODULE;

create table TOPIC_PROGRESS (
STATUS varchar(255), 
TOPIC_ID integer not null, 
HW_ID integer not null, 
primary key (TOPIC_ID, HW_ID));

alter table TOPIC_PROGRESS add constraint TOPIC_PROGRESS_FK_HW foreign key (HW_ID) references HOMEWORK;
alter table TOPIC_PROGRESS add constraint TOPIC_PROGRESS_FK_TOPIC foreign key (TOPIC_ID) references TOPIC;


-----------
-- security
-----------
create table users(
      username varchar_ignorecase(50) not null primary key,
      password varchar_ignorecase(50) not null,
      enabled boolean not null);

create table authorities (
      username varchar_ignorecase(50) not null,
      authority varchar_ignorecase(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username));
      create unique index ix_auth_username on authorities (username,authority);

--User Information
--insert into users values('staff','only',TRUE);
--insert into users values('admin','user',TRUE);
--insert into authorities values('staff','ROLE_STAFF');
--insert into authorities values('admin','ROLE_STAFF');
--insert into authorities values('admin','ROLE_ADMIN');

create table groups (
  id bigint generated by default as identity(start with 0) primary key,
  group_name varchar_ignorecase(50) not null);

create table group_authorities (
  group_id bigint not null,
  authority varchar(50) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
  id bigint generated by default as identity(start with 0) primary key,
  username varchar(50) not null,
  group_id bigint not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id));

create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null);
