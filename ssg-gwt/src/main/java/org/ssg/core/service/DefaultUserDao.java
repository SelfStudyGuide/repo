package org.ssg.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.dao.DefaultGenericDao;
import org.ssg.core.domain.Student;

@Repository(value = "UserDao")
@Transactional
public class DefaultUserDao extends DefaultGenericDao implements UserDao {

	public void saveStudent(Student st) {
		save(st);
	}

	public List<Student> getAllStudents() {
		return getAllByType(Student.class);
	}

	public Student getStudentById(int id) {
		return getById(id, Student.class);
	}

	public Student getStudentByName(String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Student.class).add(Restrictions.eq("name", name));
		List<Student> list = getHibernateTemplate().findByCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
