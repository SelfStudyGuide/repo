package org.ssg.gui.server;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.MessageSourceAccessor;

import static org.hamcrest.CoreMatchers.*;

public class ApplicationMessageSourceTest {
	private MessageSourceAccessor m = ApplicationMessageSource.getAccessor();
	
	@Test
	public void verifyThatMessageCanBeRetrivedByKey() {
		String message = m.getMessage("ssg.auth.studentNotFoundByName", new String[] {"John"});
		Assert.assertThat(message, equalTo("Student John not found"));
	}
}
