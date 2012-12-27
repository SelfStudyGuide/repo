package org.ssg.core.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.dao.DefaultGenericDao;
import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;

@Repository(value = "CurriculumDao")
@Transactional
public class DefaultCurriculumDao extends DefaultGenericDao implements CurriculumDao {

	public void saveModule(Module m) {
		save(m);
	}

	public Collection<Module> getAllModules() {
		return getAllByType(Module.class);
	}

	public Module getModuleById(int id) {
		return getById(id, Module.class);
	}

	public void deleteModule(int id) {
		delete(id, Module.class);
	}

	public List<Topic> getTopicsByModuleId(int moduleId) {
		return null;
	}

	public void saveTopic(Topic t) {
		save(t);
	}

	public Topic getTopic(int id) {
		return getById(id, Topic.class);
	}

	public void saveTask(Task task) {
		save(task);
	}

	public Task getTask(int id) {
	    return getById(id, Task.class);
    }

	public Exercise getExercise(int id) {
	    return getById(id, Exercise.class);
    }

	public void saveExercise(Exercise exercise) {
		save(exercise);
    }

}
