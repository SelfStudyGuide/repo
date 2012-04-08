package org.ssg.gui.server;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ApplicationMessageSource extends ResourceBundleMessageSource {
	public ApplicationMessageSource() {
		// messages.properties
		setBasename("org.ssg.gui.server.messages");
	}

	public static MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(new ApplicationMessageSource());
	}
}
