package org.ssg.core.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.dto.TaskType;
import org.ssg.core.support.TstDataBuilder;

@RunWith(Suite.class)
@SuiteClasses(value = { PersistanceIntegrationTest.ModulePersistanceTestCase.class,
        PersistanceIntegrationTest.TopicPersistanceTestCase.class,
        PersistanceIntegrationTest.TaskPersistanceTestCase.class,
        PersistanceIntegrationTest.HomeworkPersistanceTestCase.class,
        PersistanceIntegrationTest.StudentPersistanceTestCase.class,
        PersistanceIntegrationTest.CurriculumTreePersistanceTestCase.class, })
public class PersistanceIntegrationTest {

	public static class ModulePersistanceTestCase extends PersistanceTestCase {
		public ModulePersistanceTestCase() {
			super(Module.class, objectTestValues());
		}
	}

	public static class TopicPersistanceTestCase extends PersistanceTestCase {
		public TopicPersistanceTestCase() {
			super(Topic.class, objectTestValues());
		}
	}

	public static class TaskPersistanceTestCase extends PersistanceTestCase {
		public TaskPersistanceTestCase() {
			super(Task.class, objectTestValues());
		}
	}

	public static class HomeworkPersistanceTestCase extends PersistanceTestCase {
		public HomeworkPersistanceTestCase() {
			super(Homework.class, objectTestValues());
		}
	}

	public static class StudentPersistanceTestCase extends PersistanceTestCase {
		public StudentPersistanceTestCase() {
			super(Student.class, objectTestValues());
		}
	}

	public static class CurriculumTreePersistanceTestCase extends PersistanceTestCase {
		public CurriculumTreePersistanceTestCase() {
			super(module(), objectTreeTestValues());
		}

		private static Module module() {
			Topic topic1 = TstDataBuilder.topicBuilder().task(TstDataBuilder.taskBuilder().build())
			        .task(TstDataBuilder.taskBuilder().build()).build();
			Topic topic2 = TstDataBuilder.topicBuilder().build();

			return TstDataBuilder.moduleBuilder().topic(topic1).topic(topic2).build();
		}
	}

	public static Map<Class<?>, Value> objectTestValues() {
		Map<Class<?>, Value> values = new HashMap<Class<?>, Value>();

		values.put(int.class, new IntValue());
		values.put(String.class, new StringValue());
		values.put(TaskType.class, new TaskTypeValue());
		values.put(List.class, new EmptyListValue());

		return values;
	}

	public static Map<Class<?>, Value> objectTreeTestValues() {
		Map<Class<?>, Value> values = new HashMap<Class<?>, Value>();

		values.put(int.class, new IntValue());
		values.put(String.class, new StringValue());
		values.put(TaskType.class, new TaskTypeValue());

		return values;
	}

	public interface Value {
		Object createValue();
	}

	public static class IntValue implements Value {
		public Object createValue() {
			return RandomUtils.nextInt();
		}
	}

	public static class StringValue implements Value {
		public Object createValue() {
			char[] val = new char[10];
			int i = 0;
			while (i < val.length) {
				int intVal = RandomUtils.nextInt(90);
				if (intVal > 65 && intVal < 90) {
					val[i] = (char) intVal;
					i++;
				}
			}
			return String.valueOf(val);
		}
	}

	public static class TaskTypeValue implements Value {
		public Object createValue() {
			return TaskType.values()[RandomUtils.nextInt(TaskType.values().length - 1)];
		}
	}

	public static class EmptyListValue implements Value {
		public Object createValue() {
			return Collections.emptyList();
		}
	}

}
