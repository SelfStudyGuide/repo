package org.ssg.core.service;

import java.util.List;

import org.ssg.core.domain.Student;

public interface UserDao {

	void saveStudent(Student st);
	List<Student> getAllStudents();
	Student getStudentById(int id);
	Student getStudentByName(String name);
	
}
