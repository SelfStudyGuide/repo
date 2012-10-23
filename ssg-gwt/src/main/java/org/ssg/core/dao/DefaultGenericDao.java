package org.ssg.core.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DefaultGenericDao extends HibernateDaoSupport implements GenericDao {

	@Autowired
	public void populateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public <T> void save(T object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	public <T> T getById(int id, Class<T> objectType) {
		return getHibernateTemplate().get(objectType, id);
	}

	public <T> List<T> getAllByType(Class<T> objectType) {
		return getHibernateTemplate().loadAll(objectType);
	}

	public <T> void delete(int id, Class<T> objectType) {
		T object = getHibernateTemplate().get(objectType, id);
		if (object != null) {
			getHibernateTemplate().delete(object);
		}
	}
}
