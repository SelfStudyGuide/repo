package org.ssg.core.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.domain.Student;

@Repository(value = "UserDao")
@Transactional
public class DefaultUserDao extends HibernateDaoSupport implements UserDao {

	@Autowired
	public void populateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void saveStudent(Student st) {
		getHibernateTemplate().saveOrUpdate(st);
	}

	public List<Student> getAllStudents() {
		return getHibernateTemplate().loadAll(Student.class);
	}

	public Student getStudentById(int id) {
		return getHibernateTemplate().load(Student.class, id);
	}

}
