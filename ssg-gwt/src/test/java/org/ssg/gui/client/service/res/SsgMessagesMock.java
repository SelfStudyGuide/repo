package org.ssg.gui.client.service.res;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.ssg.gui.client.service.res.SsgLookupMessagesHelper.dot2underscore;

import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.LocalizableResource.Key;
import com.google.gwt.i18n.client.Messages;

public class SsgMessagesMock {

	public static <T extends ConstantsWithLookup> T mockLookupMsg(T mock, Class<T> clazz) {
		Properties messages = loadProperties(clazz);

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			String key = method.getAnnotation(Key.class).value();
			String message = messages.getProperty(key);
			assertNotNull("No message found for key " + key, message);
			if (methodHasZeroArgs(method)) {
				mockMessageMethod(mock, method, message);
				when(mock.getString(eq(key.replaceAll("\\.", "_")))).thenReturn(message);
			}
		}

		return mock;
	}
	
	public static <T extends ConstantsWithLookup> T mockLookupMsg(Class<T> clazz) {
		T mock = mock(clazz);

		return mockLookupMsg(mock, clazz);
	}

	public static <T extends Messages> T mockMsg(Class<T> clazz) {
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
	public void testMockMessage() {
		SsgMessages mockMessages = mockMsg(SsgMessages.class);
		String res = mockMessages.topicViewTopicStatusInProgress();
		assertThat(res, is("In Progress"));
	}

	@Test
	public void testMockLookupMessage() {
		SsgLookupMessages mockMsg = mockLookupMsg(SsgLookupMessages.class);

		assertThat(mockMsg.topic_view_notfound(), is("System cannot find topic details"));
		assertThat(mockMsg.getString(dot2underscore("topic.view.notfound")), is("System cannot find topic details"));
	}

}
