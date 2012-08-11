package org.ssg.gui.client.service;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.ssg.core.support.TstDataUtils;
import org.ssg.core.support.TstDataUtils.TestAction;

public class DefaultActionNameProviderTest {

	private DefaultActionNameProvider nameProvider;

	@Before
	public void setUp() {
		nameProvider = new DefaultActionNameProvider();
	}

	@Test
	public void verifyThatActioNameCanBeGenerated() {
		UserInfoHolder.setAppInfo(TstDataUtils.createStudentUserInfo("username", 1));
		TestAction testAction = new TstDataUtils.TestAction();

		String actionName = nameProvider.getActionName(testAction);

		String expected = "username/TstDataUtils$TestAction/";
		assertThat(actionName.substring(0, expected.length()), equalTo(expected));
	}

	@Test
	public void verifyThatAnonymousUserNameIsSentIfNoUserInfoPopulated() {
		UserInfoHolder.setAppInfo(null);
		TestAction testAction = new TstDataUtils.TestAction();

		String actionName = nameProvider.getActionName(testAction);

		// System.out.println(actionName);

		String expected = "Anonymous/TstDataUtils$TestAction/";
		assertThat(actionName.substring(0, expected.length()), equalTo(expected));
	}
}
