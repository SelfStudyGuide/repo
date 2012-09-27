------------
-- MODULE --
------------
create table MODULE (ID int4 not null, DESCRIPTION varchar(255), NAME varchar(255) not null unique, primary key (ID));

------------
-- STUDENT --
------------ 
create table STUDENT (ID int4 not null, EMAIL varchar(255), NAME varchar(255) not null, primary key (ID));

------------
-- HOMEWORK --
------------ 
create table HOMEWORK (ID int4 not null, STUD_ID int4, primary key (ID));

alter table HOMEWORK add constraint FK_HOMEWORK_STUD_ID foreign key (STUD_ID) references STUDENT;

------------
-- TOPIC --
------------ 
create table TOPIC (ID int4 not null, DESCRIPTION varchar(255), NAME varchar(255) not null, MOD_ID int4, primary key (ID));

alter table TOPIC add constraint FK_TOPIC_MOD_ID foreign key (MOD_ID) references MODULE;


------------
-- HOMEWORK_MODULE --
------------ 
create table HOMEWORK_MODULE (HW_ID int4 not null, MODULE_ID int4 not null);

alter table HOMEWORK_MODULE add constraint FK_HOMEWORK_MODULE_HW_ID foreign key (HW_ID) references HOMEWORK;
alter table HOMEWORK_MODULE add constraint FK_HOMEWORK_MODULE_MODULE_ID foreign key (MODULE_ID) references MODULE;

------------------
-- TOPIC_PROGRESS --
------------------

create table TOPIC_PROGRESS (STATUS varchar(255), TOPIC_ID int4 not null, HW_ID int4 not null, primary key (TOPIC_ID, HW_ID));

alter table TOPIC_PROGRESS add constraint FK_TOPIC_PROGRESS_HW_ID foreign key (HW_ID) references HOMEWORK;
alter table TOPIC_PROGRESS add constraint FK_TOPIC_PROGRESS_TOPIC_ID foreign key (TOPIC_ID) references TOPIC;

---------------
-- TOPIC_TASK --
---------------

create table TOPIC_TASK (ID int4 not null, DESCRIPTION TEXT, EXECRCISE_CNT int4, NAME varchar(255) not null, TYPE varchar(255) not null, TOPIC_ID int4, primary key (ID));
alter table TOPIC_TASK add constraint FK_TOPIC_TASK_TOPIC_ID foreign key (TOPIC_ID) references TOPIC;

create sequence hibernate_sequence;

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
  id bigint primary key,
  group_name varchar(50) not null);

create table group_authorities (
  group_id bigint not null,
  authority varchar(50) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
  id bigint primary key,
  username varchar(50) not null,
  group_id bigint not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id));
        