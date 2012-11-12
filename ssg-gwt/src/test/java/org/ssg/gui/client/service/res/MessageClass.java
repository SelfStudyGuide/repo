package org.ssg.gui.client.service.res;

import org.mockito.Mockito;

public class MessageClass<T> {

	private final Class<T> aMessageClass;

	public MessageClass(Class<T> aMessageClass) {
		this.aMessageClass = aMessageClass;
	}

	public static <T> MessageClass<T> forClass(Class<T> aMessageClass) {
		return new MessageClass<T>(aMessageClass);
	}

	public T createMockInstance() {
		return Mockito.mock(aMessageClass);
	}

	public MessagePropertyFile loadPropertiesFile() {
		return MessagePropertyFile.loadBy(aMessageClass);
	}

}
