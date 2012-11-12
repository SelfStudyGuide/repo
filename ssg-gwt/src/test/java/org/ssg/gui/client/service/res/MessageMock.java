package org.ssg.gui.client.service.res;

public class MessageMock<T> {

	private final MessageClass<T> aMessageClass;

	public MessageMock(MessageClass<T> aMessageClass) {
		this.aMessageClass = aMessageClass;
	}

	public static <T> MessageMock<T> instanceFor(MessageClass<T> aMessageClass) {
		return new MessageMock<T>(aMessageClass);
	}

	public T mock() {
		return new MessageMockBuilder<T>(aMessageClass).buildMockInstance();
	}

}
