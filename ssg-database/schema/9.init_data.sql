insert into MODULE(id,desc, name) values(2, 'It is the first module', '1st module');
insert into STUDENT(id, name, email) values(1, 'jd', 'john.dou@email.com');
insert into HOMEWORK(id, stud_id) values(3, 1);
insert into HOMEWORK_MODULE(HW_ID, MODULE_ID) values(3, 2);
insert into TOPIC(id, name, mod_id) values(1, 'The first topic', 2);
insert into TOPIC(id, name, mod_id) values(2, 'The second topic', 2);
insert into TOPIC(id, name, mod_id) values(3, 'The third topic', 2);
insert into TOPIC_PROGRESS(STATUS, TOPIC_ID, HW_ID) values('0', 1, 3)
insert into TOPIC_PROGRESS(STATUS, TOPIC_ID, HW_ID) values('10', 2, 3)
insert into TOPIC_PROGRESS(STATUS, TOPIC_ID, HW_ID) values('100', 3, 3)

insert into MODULE(id,desc, name) values(3, 'It is the sedond module', '2nd module');
insert into HOMEWORK(id, stud_id) values(4, 1);
insert into HOMEWORK_MODULE(HW_ID, MODULE_ID) values(3, 3);
insert into TOPIC(id, name, mod_id) values(4, 'The 4rd topic', 3);
insert into TOPIC_PROGRESS(STATUS, TOPIC_ID, HW_ID) values('100', 4, 4)

insert into users values('jd','1',TRUE)
insert into authorities values('jd','student');