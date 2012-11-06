create table EXERCISE (
  ID int4 not null, 
  DESCRIPTION varchar(255), 
  TYPE varchar(255) not null, 
  NAME varchar(255) not null, 
  EXERCISE_ORDER int4 not null, 
  TASK_ID int4, 
  primary key (ID)
);

alter table EXERCISE add constraint FK_EXERCISE_TASK_ID foreign key (TASK_ID) references TOPIC_TASK;
