package org.ssg.core.service;

import static org.ssg.core.support.databuilder.ExerciseBuilder.exerciseBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.ExerciseType;
import org.ssg.core.dto.TaskType;
import org.ssg.core.support.TstDataBuilder;

@RunWith(Suite.class)
@SuiteClasses(value = { PersistanceIntegrationTest.ModulePersistanceTestCase.class,
        PersistanceIntegrationTest.TopicPersistanceTestCase.class,
        PersistanceIntegrationTest.TaskPersistanceTestCase.class,
        PersistanceIntegrationTest.HomeworkPersistanceTestCase.class,
        PersistanceIntegrationTest.StudentPersistanceTestCase.class,
        PersistanceIntegrationTest.CurriculumTreePersistanceTestCase.class,
        PersistanceIntegrationTest.ExercisePersistanceTestCase.class
        /*PersistanceIntegrationTest.TopicProgressPersistanceTestCase.class*/})
public class PersistanceIntegrationTest {

	public static class HomeworkTreePersistanceTestCase extends PersistanceTestCase {
		public HomeworkTreePersistanceTestCase() {
			super(Module.class, objectTestValues());
		}
	}
	
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
	
	public static class TopicProgressPersistanceTestCase extends PersistanceTestCase {
		public TopicProgressPersistanceTestCase() {
			super(TopicProgress.class, objectTestValues());
		}
	}

	public static class StudentPersistanceTestCase extends PersistanceTestCase {
		public StudentPersistanceTestCase() {
			super(Student.class, objectTestValues());
		}
	}

	public static class ExercisePersistanceTestCase extends PersistanceTestCase {
		public ExercisePersistanceTestCase() {
			super(Exercise.class, objectTestValues());
		}
	}

	public static class CurriculumTreePersistanceTestCase extends PersistanceTestCase {
		public CurriculumTreePersistanceTestCase() {
			super(module(), objectTreeTestValues());
		}

		private static Module module() {
			Task task = TstDataBuilder.taskBuilder().exercise(exerciseBuilder().name("ex").build())
			        .build();
			Topic topic1 = TstDataBuilder.topicBuilder().task(task).task(TstDataBuilder.taskBuilder().build()).build();
			Topic topic2 = TstDataBuilder.topicBuilder().build();

			return TstDataBuilder.moduleBuilder().topic(topic1).topic(topic2).build();
		}
	}

	public static Map<Class<?>, Value> objectTestValues() {
		Map<Class<?>, Value> values = new HashMap<Class<?>, Value>();

		values.put(int.class, new IntValue());
		values.put(String.class, new StringValue());
		values.put(TaskType.class, new EnumValue(TaskType.values()));
		values.put(List.class, new EmptyListValue());
		values.put(ExerciseType.class, new EnumValue(ExerciseType.values()));

		return values;
	}

	public static Map<Class<?>, Value> objectTreeTestValues() {
		Map<Class<?>, Value> values = new HashMap<Class<?>, Value>();

		values.put(int.class, new IntValue());
		values.put(String.class, new StringValue());
		values.put(TaskType.class, new EnumValue(TaskType.values()));
		values.put(ExerciseType.class, new EnumValue(ExerciseType.values()));
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

	public static class EnumValue implements Value {
		private Enum<?>[] values;

		public EnumValue(Enum<?>[] values) {
			this.values = values;
		}

		public Object createValue() {
			return values[RandomUtils.nextInt(values.length)];
		}
	}

	public static class EmptyListValue implements Value {
		public Object createValue() {
			return Collections.emptyList();
		}
	}

}
