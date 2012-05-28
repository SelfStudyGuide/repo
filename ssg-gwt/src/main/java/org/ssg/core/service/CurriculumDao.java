package org.ssg.core.service;

import java.util.Collection;
import java.util.List;

import org.ssg.core.domain.Module;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;

public interface CurriculumDao {
	void saveModule(Module m);
	Collection<Module> getAllModules();
	Module getModuleById(int id);
	void deleteModule(int id);
	
	List<Topic> getTopicsByModuleId(int moduleId);
	void saveTopic(Topic t);
	void saveTask(Task task);
	
}
