package org.ssg.gui.client.service.res;

public class MessageMockBuilder<T> {

	private final MessageClass<T> aMessageClass;

	public MessageMockBuilder(MessageClass<T> aMessageClass) {
		this.aMessageClass = aMessageClass;
    }

	public T buildMockInstance() {
		T instance = aMessageClass.createMockInstance();
		MessagePropertyFile propertyFile = aMessageClass.loadPropertiesFile();
		//aMessageClass.
		
		return null;
    }
	
}
