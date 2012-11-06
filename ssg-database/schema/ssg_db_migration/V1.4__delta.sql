create table EXERCISE (
  ID integer not null auto_increment, 
  DESCRIPTION varchar(255), 
  TYPE varchar(255) not null, 
  NAME varchar(255) not null, 
  EXERCISE_ORDER integer not null, 
  TASK_ID integer, 
  primary key (ID)
);

alter table EXERCISE add index IX_EXERCISE_TASK_ID (TASK_ID), add constraint FK_EXERCISE_TASK_ID foreign key (TASK_ID) references TOPIC_TASK (ID);

