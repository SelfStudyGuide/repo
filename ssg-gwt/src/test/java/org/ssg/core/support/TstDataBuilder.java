package org.ssg.core.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.ExerciseType;
import org.ssg.core.dto.TaskType;

public class TstDataBuilder {

	public static ModuleBuilder moduleBuilder() {
		return new ModuleBuilder();
	}

	public static class ModuleBuilder {
		private Module module = new Module();

		public Module build() {
			return module;
		}

		public ModuleBuilder id(int id) {
			module.setId(id);
			return this;
		}

		public ModuleBuilder name(String name) {
			module.setName(name);
			return this;
		}

		public ModuleBuilder description(String description) {
			module.setDescription(description);
			return this;
		}

		public ModuleBuilder topic(Topic topic) {
			List<Topic> topics = module.getTopics();
			if (topics == null) {
				topics = new ArrayList<Topic>();
			}
			topic.setModule(module);
			topics.add(topic);
			module.setTopics(topics);
			return this;
		}
	}

	public static TaskBuilder taskBuilder() {
		return new TaskBuilder();
	}

	public static class TaskBuilder {
		private Task task = new Task();

		public TaskBuilder id(int id) {
			task.setId(id);
			return this;
		}

		public TaskBuilder name(String name) {
			task.setName(name);
			return this;
		}

		public TaskBuilder type(TaskType type) {
			task.setType(type);
			return this;
		}

		public TaskBuilder execrisesCount(int cnt) {
			task.setExecrisesCount(cnt);
			return this;
		}

		public Task build() {
			return task;
		}

		public TaskBuilder description(String d) {
			task.setDescription(d);
			return this;
		}

		public TaskBuilder exercise(Exercise e) {
			e.setTask(task);

			List<Exercise> exercises = task.getExercises();

			if (exercises == null) {
				exercises = new ArrayList<Exercise>();
			}

			exercises.add(e);
			task.setExercises(exercises);
			
			return this;
		}

	}

	public static TopicBuilder topicBuilder() {
		return new TopicBuilder();
	}

	public static class TopicBuilder {

		private Topic topic = new Topic();

		public Topic build() {
			return topic;
		}

		public TopicBuilder id(int id) {
			topic.setId(id);
			return this;
		}

		public TopicBuilder name(String name) {
			topic.setName(name);
			return this;
		}

		public TopicBuilder description(String descr) {
			topic.setDescription(descr);
			return this;
		}

		public TopicBuilder module(Module module) {
			topic.setModule(module);
			return this;
		}

		public TopicBuilder unigueName(String prefix) {
			topic.setName(prefix + " " + System.currentTimeMillis());
			return this;
		}

		public TopicBuilder task(Task task) {
			task.setTopic(topic);

			List<Task> tasks = topic.getTasks();
			if (tasks == null) {
				topic.setTasks(new ArrayList<Task>(Arrays.asList(task)));
			} else {
				tasks.add(task);
				topic.setTasks(tasks);
			}
			return this;
		}

	}

	public static TopicProgressBuilder topicProgressBuilder() {
		return new TopicProgressBuilder();
	}

	public static class TopicProgressBuilder {

		private TopicProgress topicProgress = new TopicProgress();

		public TopicProgress build() {
			return topicProgress;
		}

		public TopicProgressBuilder homework(Homework homework) {
			topicProgress.setHomework(homework);
			setTopicProgressToHomework(topicProgress, homework);
			return this;
		}

		public TopicProgressBuilder topic(Topic topic) {
			topicProgress.setTopic(topic);
			return this;
		}
		
		public TopicProgressBuilder status(String status) {
			topicProgress.setStatus(status);
			return this;
		}

	}

	public static HomeworkBuilder homeworkBuilder() {
		return new HomeworkBuilder();
	}

	private static void setTopicProgressToHomework(TopicProgress progress, Homework homework) {
		List<TopicProgress> progresses = homework.getProgresses();
		if (progresses == null) {
			homework.setProgresses(new ArrayList<TopicProgress>(Arrays.asList(progress)));
		} else {
			progresses.add(progress);
			homework.setProgresses(progresses);
		}
	}

	public static class HomeworkBuilder {

		private Homework homework = new Homework();

		public HomeworkBuilder id(int id) {
			homework.setId(id);
			return this;
		}

		public HomeworkBuilder module(Module module) {
			homework.setModule(module);
			return this;
		}

		public HomeworkBuilder student(Student student) {
			homework.setStudent(student);

			List<Homework> homeworks = student.getHomeworks();
			if (homeworks == null) {
				student.setHomeworks(new ArrayList<Homework>(Arrays.asList(homework)));
			} else {
				homeworks.add(homework);
				student.setHomeworks(homeworks);
			}

			return this;
		}

		public HomeworkBuilder topicProgress(TopicProgress progress) {
			progress.setHomework(homework);
			setTopicProgressToHomework(progress, homework);
			return this;
		}

		public Homework build() {
			return homework;
		}
	}
}
