package org.ssg.gui.client.service.res;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.ssg.gui.client.service.res.SsgLookupMessagesHelper.dot2underscore;

import java.lang.reflect.Method;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
	
	public static <T extends Messages> T mockMsgNew(MessageClass<T> aMessageClass) {
		
		T message = MessageMock.instanceFor(aMessageClass).mock();
		
		return message;
	}

	public static <T extends Messages> T mockMsg(Class<T> clazz) {
		T mock = mock(clazz);
		Properties messages = loadProperties(clazz);

		Method[] methods = clazz.getDeclaredMethods();

		for (Method method : methods) {
			
			String key = method.getAnnotation(Key.class).value();
			final String message = messages.getProperty(key);
			assertNotNull("No message found for key " + key, message);

			Class<?>[] parameterTypes = method.getParameterTypes();
			
			try {
				
				Object[] matchers = new Object[parameterTypes.length];
			
				for (int i = 0; i < parameterTypes.length; i++) {
					Class<?> paramType = parameterTypes[i];
					matchers[i] = isA(paramType);
				}

				when(method.invoke(mock, matchers)).thenAnswer(messageComposer(message));

			} catch (Exception e) {
				Assert.fail("Cannot mock mothod " + method + " for class " + mock.getClass() + "\nError: "
				        + e.getMessage());
			}
		}

		return mock;

	}

	private static Answer<String> messageComposer(final String message) {
		return new Answer<String>() {
			public String answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				String messageToReturn = message;
				for (int i = 0; i < args.length; i++) {
					Object arg = args[i];
					messageToReturn = messageToReturn.replace("{" + i + "}", arg.toString());
				}
				return messageToReturn;
			}
		};
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
	public void givenMessageWithParameterThenMessageSholdContainItsValue() {
		SsgMessages mockMessages = mockMsg(SsgMessages.class);
		String message = mockMessages.serviceErrorUnexpected("foo");
		assertThat(message, is("User action cannot be processed by server.<br/>Error: foo."));
	}

	@Test
	public void testMockMessageNew() {
		SsgMessages mockMessages = mockMsgNew(MessageClass.forClass(SsgMessages.class));
		String res = mockMessages.topicViewTopicStatusInProgress();
		assertThat(res, is("In Progress"));
	}

	@Test
	public void givenMessageWithParameterThenMessageSholdContainItsValueNew() {
		SsgMessages mockMessages = mockMsgNew(MessageClass.forClass(SsgMessages.class));
		String message = mockMessages.serviceErrorUnexpected("foo");
		assertThat(message, is("User action cannot be processed by server.<br/>Error: foo."));
	}
	
	@Test
	public void testMockLookupMessage() {
		SsgLookupMessages mockMsg = mockLookupMsg(SsgLookupMessages.class);

		assertThat(mockMsg.topic_view_notfound(), is("System cannot find topic details"));
		assertThat(mockMsg.getString(dot2underscore("topic.view.notfound")), is("System cannot find topic details"));
	}

}
