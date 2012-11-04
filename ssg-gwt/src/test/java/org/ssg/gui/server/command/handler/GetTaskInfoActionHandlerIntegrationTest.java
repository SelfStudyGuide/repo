package org.ssg.gui.server.command.handler;

import java.io.InputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;

@ActiveProfiles(inheritProfiles = false, profiles = { "junit", "real-core", "junit-mock-security", "real-gui" })
public class GetTaskInfoActionHandlerIntegrationTest extends AbstractCommandTestCase<GetTaskInfoResponse, GetTaskInfo> {

	@Autowired
	private IDatabaseTester databaseTester;

	@Before
	public void setUp() throws Exception {
		InputStream stream = this.getClass().getResourceAsStream("/dbunit/GetTaskInfoActionHandlerIntegrationTest.xml");
		IDataSet dataSet = new FlatXmlDataSet(stream);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	@After
	public void tearDown() throws Exception {
		databaseTester.onTearDown();
	}

	@Test
	public void verifySunnyDayScenario() {
		// Given
		GetTaskInfo action = new GetTaskInfo(10, 20);

		// When
		GetTaskInfoResponse response = whenAction(action);

		// Then
		Assert.assertThat(response.getTaskInfo().getId(), CoreMatchers.is(20));
	}

	@Override
	protected Class<GetTaskInfo> testedCommandClass() {
		return GetTaskInfo.class;
	}
}
