package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.domain.Homework;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.server.command.AbstractCommandTestCase;

@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml",
		"/serice-core-mock.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GetTopicInfoActionHandlerTest extends
		AbstractCommandTestCase<GetTopicInfoResponse, GetTopicInfo> {

	@Override
	protected Class<GetTopicInfo> testedCommandClass() {
		return GetTopicInfo.class;
	}

	@Autowired
	private HomeworkDao mockHomeworkDao;

	@Test
	public void verifyThatTopicInfoIsReturnedByGivenTopicIdAndHomeworkId() {
		// Given
		Mockito.when(mockHomeworkDao.getHomework(Matchers.eq(123))).thenReturn(
				createHomework());

		// When
		GetTopicInfoResponse action = whenAction(new GetTopicInfo(123, 345));

		// Then
		assertThat(action, notNullValue());
		assertThat(action.getInfo().getTopicId(), CoreMatchers.is(345));
		assertThat(action.getInfo().getTopicName(),
				CoreMatchers.is("Test topic 1"));
		assertThat(action.getInfo().getDescription(),
				CoreMatchers.is("Description of topic"));
		assertThat(action.getInfo().getStatus(), CoreMatchers.is("10"));
	}

	private Homework createHomework() {
		Homework hw = TstDataUtils.createHomework(
				TstDataUtils.createStudent("jd"), TstDataUtils.createModule());
		TstDataUtils.enrichHomeworkWithProgress(hw,
				TstDataUtils.createTopic("Name", 345));
		return hw;
	}

}
