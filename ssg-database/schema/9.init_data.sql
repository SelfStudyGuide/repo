insert into MODULE(id,desc, name) values(2, 'It is the first module', '1st module');
insert into STUDENT(id, name, email) values(1, 'jd', 'john.dou@email.com');
insert into HOMEWORK(id, stud_id) values(3, 1);
insert into HOMEWORK_MODULE(HW_ID, MODULE_ID) values(3, 2);

insert into users values('jd','1',TRUE)
insert into authorities values('jd','student');