package org.ssg.core.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.dao.DefaultGenericDao;

@Repository("TestGenericDao")
public class TestGenericDao extends DefaultGenericDao {

	public <T> void deleteAllByType(Class<T> objectType) {
		List<T> allByType = getAllByType(objectType);
		for (T t : allByType) {
			getHibernateTemplate().delete(t);
		}

	}
}
