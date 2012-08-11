package org.ssg.gui.client.service.res;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.i18n.client.LocalizableResource.Key;

public class SsgMessagesMock {

	public static <T> T mockMsg(Class<T> clazz) {
		T mock = mock(clazz);
		Properties messages = loadProperties(clazz);

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			String key = method.getAnnotation(Key.class).value();
			String message = messages.getProperty(key);
			assertNotNull("No message found for key " + key, message);
			if (methodHasZeroArgs(method)) {
				mockMessageMethod(mock, method, message);
			}
		}

		return mock;

	}

	private static <T> void mockMessageMethod(T mock, Method method, String message) {
		try {
			when(method.invoke(mock, (Object[]) null)).thenReturn(message);
		} catch (Exception e) {
			Assert.fail("Cannot mock mothod " + method + " for class " + mock.getClass());
		}
	}

	private static boolean methodHasZeroArgs(Method method) {
		return method.getParameterTypes().length == 0;
	}

	private static <T> Properties loadProperties(Class<T> clazz) {
		try {
			Properties p = new Properties();
			p.load(clazz.getResourceAsStream(clazz.getSimpleName() + ".properties"));
			return p;
		} catch (Exception e) {
			Assert.fail("Cannot load " + clazz.getSimpleName() + ".properties");
			return null;
		}
	}

	@Test
	public void testMock() {
		SsgMessages mockMessages = mockMsg(SsgMessages.class);
		String res = mockMessages.topicViewTopicStatusInProgress();
		Assert.assertThat(res, is("In Progress"));
	}

}
