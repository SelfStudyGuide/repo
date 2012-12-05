package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;

public class GetHomeworksActionHandlerIntegrationTest extends AbstractCommandIntegrationTestCase<GetHomeworksResponse, GetHomeworks> {

	
	private static final int STUDENT_ID = 30;

	@Before
	public void setUp() throws Exception {
		setUpData("/dbunit/GetHomeworksActionHandlerIntegrationTest.xml");
	}
	
	@After
	public void tearDown() throws Exception {
		cleanUpData();
	}
	
	@Test
	public void verifyThatListOfHomeworksAreLoaded() {
		// Given
		GetHomeworks getHomeworks = new GetHomeworks(STUDENT_ID);

		// When
		GetHomeworksResponse response = whenAction(getHomeworks);

		// Then
		assertThat(response.getHomeworks().size(), is(1));
		HomeworkInfo homeworkInfo = response.getHomeworks().iterator().next();
		assertThat(homeworkInfo.getId(), is(10));
		assertThat(homeworkInfo.getAssignedModules(), is("One"));
	}
	
	@Override
    protected Class<GetHomeworks> testedCommandClass() {
		return GetHomeworks.class;
	}

}
