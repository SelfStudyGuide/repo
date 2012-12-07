package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;

public class GetHomeworkDetailsActionHandlerIntegrationTest extends
        AbstractCommandIntegrationTestCase<GetHomeworkDetailsResponse, GetHomeworkDetails> {

	private static final int STUDENT_ID = 30;
	private static final int HW_ID = 10;
	
	@Before
	public void setUp() throws Exception {
		setUpData("/dbunit/GetHomeworkDetailsActionHandlerIntegrationTest.xml");
	}
	
	@After
	public void tearDown() throws Exception {
		cleanUpData();
	}
	
	@Test
	public void givenGetHomeworkDetailsActionThen() {
		// Given
		GetHomeworkDetails getHomeworkDetails = new GetHomeworkDetails(STUDENT_ID, HW_ID);

		// When
		GetHomeworkDetailsResponse response = whenAction(getHomeworkDetails);
		
		// Then
		assertThat(response.getHomework(), notNullValue());
		assertThat(response.getHomework().getTopicProgress().size(), CoreMatchers.is(1));
		
	}
	
	@Override
	protected Class<GetHomeworkDetails> testedCommandClass() {
		return GetHomeworkDetails.class;
	}

}
