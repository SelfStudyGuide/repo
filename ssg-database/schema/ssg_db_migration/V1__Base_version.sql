------------
-- MODULE --
------------
create table MODULE (
ID integer not null auto_increment, 
`DESC` varchar(255), 
NAME varchar(255) not null unique, 
primary key (ID));

------------
-- STUDENT --
------------ 
create table STUDENT (
ID integer not null auto_increment, 
EMAIL varchar(255), 
NAME varchar(255) not null, 
primary key (ID));


------------
-- HOMEWORK --
------------ 
create table HOMEWORK (
ID integer not null auto_increment, 
STUD_ID integer, 
primary key (ID));

alter table HOMEWORK add index FK11BB2470D751A4B9 (STUD_ID), add constraint HOMEWORK_FK_STUDENT foreign key (STUD_ID) references STUDENT (ID);

------------
-- TOPIC --
------------ 
create table TOPIC (
ID integer not null auto_increment, 
`DESC` varchar(255), 
NAME varchar(255) not null, 
MOD_ID integer, 
primary key (ID));

alter table TOPIC add index FK4C4D50FD0D383F0 (MOD_ID), add constraint TOPIC_FK_MOD foreign key (MOD_ID) references MODULE (ID);


------------
-- HOMEWORK_MODULE --
------------ 
create table HOMEWORK_MODULE (
HW_ID integer not null, 
MODULE_ID integer not null);

alter table HOMEWORK_MODULE add index FKDC06951B1C4881C7 (HW_ID), add constraint HOMEWORK_MODULE_FK_HW foreign key (HW_ID) references HOMEWORK (ID);
alter table HOMEWORK_MODULE add index FKDC06951BB4055446 (MODULE_ID), add constraint HOMEWORK_MODULE_FK_MOD foreign key (MODULE_ID) references MODULE (ID);

create table TOPIC_PROGRESS (
STATUS varchar(255), 
TOPIC_ID integer not null, 
HW_ID integer not null, 
primary key (TOPIC_ID, HW_ID));

alter table TOPIC_PROGRESS add index FK7B884F5D1C4881C7 (HW_ID), add constraint TOPIC_PROGRESS_FK_HW foreign key (HW_ID) references HOMEWORK (ID);
alter table TOPIC_PROGRESS add index FK7B884F5D85CBB3CE (TOPIC_ID), add constraint TOPIC_PROGRESS_FK_TOPIC foreign key (TOPIC_ID) references TOPIC (ID);

-----------
-- security
-----------
create table users(
      username varchar(50) not null primary key,
      password varchar(50) not null,
      enabled boolean not null);

create table authorities (
      username varchar(50) not null,
      authority varchar(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username));
      create unique index ix_auth_username on authorities (username,authority);


create table groups (
  id integer not null auto_increment,
  group_name varchar(50) not null,
  primary key(id));

create table group_authorities (
  group_id integer not null,
  authority varchar(50) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table   group_members (
  id integer not null auto_increment,
  username varchar(50) not null,
  group_id integer not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id),
  primary key(id));

create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null);
