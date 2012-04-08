package org.ssg.core.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.domain.Homework;

@Repository
@Transactional
public class DefaultHomeWorkDao extends HibernateDaoSupport implements
		HomeworkDao {

	@Autowired
	public void populateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void saveHomework(Homework homework) {
		getHibernateTemplate().saveOrUpdate(homework);
		getHibernateTemplate().flush();
	}

	public Homework getHomework(int homeworkId) {
		return getHibernateTemplate().get(Homework.class, homeworkId);
	}
}
