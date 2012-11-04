package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Homework;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.server.security.Authorization;

// GetTopicInfoActionHandler
public class GetTopicInfoActionHandlerTest extends AbstractCommandTestCase<GetTopicInfoResponse, GetTopicInfo> {

	private static final int TOPIC_ID = 345;

	private static final int HW_ID = 123;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Override
	protected Class<GetTopicInfo> testedCommandClass() {
		return GetTopicInfo.class;
	}

	@Autowired
	private HomeworkDao mockHomeworkDao;

	@Autowired
	private Authorization mockAuthorization;
	
	@Before
	public void setUp() {
		reset(mockAuthorization, mockHomeworkDao);
	}
	
	@Test
	public void verifyThatTopicInfoIsReturnedByGivenTopicIdAndHomeworkId() {
		// Given
		when(mockHomeworkDao.getHomework(Matchers.eq(HW_ID))).thenReturn(createHomework());

		// When
		GetTopicInfoResponse action = whenAction(new GetTopicInfo(HW_ID, TOPIC_ID));

		// Then
		assertThat(action, notNullValue());
		assertThat(action.getInfo().getTopicId(), CoreMatchers.is(345));
		assertThat(action.getInfo().getTopicName(), CoreMatchers.is("Test topic 1"));
		assertThat(action.getInfo().getDescription(), CoreMatchers.is("Description of topic"));
		assertThat(action.getInfo().getStatus(), CoreMatchers.is("10"));
	}

	@Test
	public void verifyThatIfHomeworkCannotBeFoundByGivenIdThenExceptionIsThrown() {
		// Given
		when(mockHomeworkDao.getHomework(Matchers.eq(123))).thenReturn(null);

		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("topic.view.notfound")));
		
		// When
		GetTopicInfoResponse action = whenAction(new GetTopicInfo(HW_ID, TOPIC_ID));

	}

	@Test
	public void verifyThatIfTopicCannotBeFoundByGivenIdThenExceptionIsThrown() {
		// Given
		when(mockHomeworkDao.getHomework(Matchers.eq(123))).thenReturn(createHomework());
		
		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("topic.view.notfound")));

		// When
		whenAction(new GetTopicInfo(HW_ID, 678));
	}
	
	@Test
	public void verifyThatOwnHomeworkIsCalledWhenGetTopicInfoRequest() {
		// Given
		Homework hw = createHomework();
		when(mockHomeworkDao.getHomework(Matchers.eq(HW_ID))).thenReturn(hw);

		// When
		whenAction(new GetTopicInfo(HW_ID, TOPIC_ID));
		
		// Then
		verify(mockAuthorization).ownHomework(Mockito.same(hw));
	}
	

	private Homework createHomework() {
		Homework hw = TstDataUtils.createHomework(TstDataUtils.createStudent("jd"), TstDataUtils.createModule());
		TstDataUtils.enrichHomeworkWithProgress(hw, TstDataUtils.createTopic("Name", TOPIC_ID));
		return hw;
	}
}
