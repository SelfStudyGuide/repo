package org.ssg.gui.server;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.ssg.core.dto.FieldType;
import org.ssg.core.dto.TaskType;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;

@RunWith(Parameterized.class)
public class GwtRpcTest {

	private final Class<?> clazz;

	public GwtRpcTest(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Test
	public void verifyThatClassIsSerializable() {
		List<Class<?>> interfaces = ClassUtils.getAllInterfaces(clazz);

		boolean isImplements = false;
		for (Class<?> i : interfaces) {
			isImplements |= i.isAssignableFrom(Serializable.class);
		}

		assertTrue("Class " + clazz.getName()
				+ " should implements Serializable", isImplements);
	}

	@Test
	public void verifyThatClassHasDefaultConstructor() {
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			assertThat(constructor, CoreMatchers.notNullValue());
		} catch (NoSuchMethodException e) {
			fail("Class " + clazz.getName() + " should have default contructor");
		}
	}

	@Parameters
	public static Collection<Object[]> collectClasses()
			throws ClassNotFoundException {

		Collection<Object[]> result = new ArrayList<Object[]>();

		actionsAndResponses(result);
		dtoObjects(result);

		return result;
	}

	private static void dtoObjects(Collection<Object[]> result)
			throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider scaner = new ClassPathScanningCandidateComponentProvider(
				false);
		scaner.addIncludeFilter(new TypeFilter() {

			public boolean match(MetadataReader metadataReader,
					MetadataReaderFactory metadataReaderFactory)
					throws IOException {
				return true;
			}
		});

		scaner.addExcludeFilter(new AssignableTypeFilter(FieldType.class));
		scaner.addExcludeFilter(new AssignableTypeFilter(TaskType.class));

		Set<BeanDefinition> beans = scaner
				.findCandidateComponents("org/ssg/core/dto");

		for (BeanDefinition bean : beans) {
			result.add(new Object[] { Class.forName(bean.getBeanClassName()) });
		}
	}

	private static void actionsAndResponses(Collection<Object[]> result)
			throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider scaner = new ClassPathScanningCandidateComponentProvider(
				false);
		scaner.addIncludeFilter(new AssignableTypeFilter(Action.class));
		scaner.addIncludeFilter(new AssignableTypeFilter(Response.class));
		
		Set<BeanDefinition> beans = scaner
				.findCandidateComponents("org/ssg/gui");

		for (BeanDefinition bean : beans) {
			result.add(new Object[] { Class.forName(bean.getBeanClassName()) });
		}
	}

}
