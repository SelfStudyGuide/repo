create table EXERCISE (
  ID integer not null auto_increment, 
  DESCRIPTION varchar(255), 
  TYPE varchar(255) not null, 
  NAME varchar(255) not null, 
  EXERCISE_ORDER integer not null, 
  TOPIC_ID integer, 
  primary key (ID)
);

alter table EXERCISE add index IX_EXERCISE_TOPIC_ID (TOPIC_ID), add constraint FK_EXERCISE_TOPIC_ID foreign key (TOPIC_ID) references TOPIC_TASK (ID);

