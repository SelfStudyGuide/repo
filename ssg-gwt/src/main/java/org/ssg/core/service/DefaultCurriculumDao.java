package org.ssg.core.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.domain.Module;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;

@Repository(value = "CurriculumDao")
@Transactional
public class DefaultCurriculumDao extends HibernateDaoSupport implements
		CurriculumDao {

	@Autowired
	public void populateSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void saveModule(Module m) {
		getHibernateTemplate().saveOrUpdate(m);
	}

	public Collection<Module> getAllModules() {
		return getHibernateTemplate().loadAll(Module.class);
	}

	public Module getModuleById(int id) {
		return getHibernateTemplate().get(Module.class, id);
	}

	public void deleteModule(int id) {
		Module module = getHibernateTemplate().get(Module.class, id);
		if (module != null) {
			getHibernateTemplate().delete(module);
		}
	}

	public List<Topic> getTopicsByModuleId(int moduleId) {
		return null;
	}

	public void saveTopic(Topic t) {
		getHibernateTemplate().saveOrUpdate(t);
	}

	public Topic getTopic(int id) {
		return getHibernateTemplate().get(Topic.class, id);
	}

	public void saveTask(Task task) {
		getHibernateTemplate().saveOrUpdate(task);
	}
	
	

}
