------------
-- TOPIC_TASK --
------------
create table TOPIC_TASK (
ID integer not null auto_increment, 
NAME varchar(255) not null, 
TYPE varchar(64) not null,
TOPIC_ID integer,
primary key (ID));

alter table TOPIC_TASK add index TOPIC_TASK_IX1 (TOPIC_ID), add constraint TOPIC_TASK_FK_TOPIC foreign key (TOPIC_ID) references TOPIC (ID);

