package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.moduleBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.TstDataBuilder.topicProgressBuilder;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.server.security.Authorization;

// GetTopicInfoActionHandler
public class GetTopicInfoActionHandlerTest extends AbstractCommandTestCase<GetTopicInfoResponse, GetTopicInfo> {

	private static final int TOPIC_ID = 345;
	
	private static final int WRONG_TOPIC_ID = 346;

	private static final int HW_ID = 123;
	
	private static final int WRONG_HW_ID = 124;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Override
	protected Class<GetTopicInfo> testedCommandClass() {
		return GetTopicInfo.class;
	}

	@Autowired
	private HomeworkDao mockHomeworkDao;

	@Autowired 
	private CurriculumDao curriculumDao;
	
	@Autowired
	private Authorization mockAuthorization;

	private Homework homework;

	private TopicProgress progress;

	private Topic topic;
	
	@Before
	public void setUp() {
		reset(mockAuthorization, mockHomeworkDao);
		// Given
		createTestData();
		when(mockHomeworkDao.getHomework(Matchers.eq(HW_ID))).thenReturn(homework);
		when(curriculumDao.getTopic(TOPIC_ID)).thenReturn(topic);
	}
	
	@Test
	public void verifyThatTopicInfoIsReturnedByGivenTopicIdAndHomeworkId() {
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
		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("topic.view.notfound")));
		
		// When
		GetTopicInfoResponse action = whenAction(new GetTopicInfo(WRONG_HW_ID, TOPIC_ID));

	}

	@Test
	public void verifyThatIfTopicCannotBeFoundByGivenIdThenExceptionIsThrown() {
		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("topic.view.notfound")));

		// When
		whenAction(new GetTopicInfo(HW_ID, WRONG_TOPIC_ID));
	}
	
	@Test
	public void verifyThatOwnHomeworkIsCalledWhenGetTopicInfoRequest() {
		// When
		whenAction(new GetTopicInfo(HW_ID, TOPIC_ID));
		
		// Then
		verify(mockAuthorization).ownHomework(Mockito.same(homework));
	}

	private void createTestData() {
		Module module = moduleBuilder().name("name").description("desc").build();
		topic = topicBuilder().id(TOPIC_ID).description("Description of topic").name("Test topic 1").build();
		Student student = TstDataUtils.createStudent("jd");
		progress = topicProgressBuilder().topic(topic).status("10").build();
		homework = homeworkBuilder().id(HW_ID).module(module).topicProgress(progress).student(student).build();
	}
}
