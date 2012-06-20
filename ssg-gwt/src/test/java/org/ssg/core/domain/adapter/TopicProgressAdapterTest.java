package org.ssg.core.domain.adapter;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.core.support.TstDataUtils;

public class TopicProgressAdapterTest {
	
	private TopicProgress topicProgress;
	private TopicProgressAdapter adapter;
	
	@Before
	public void setUp() {
		Topic topic = TstDataUtils.createTopic("Some topic", 123);
		topic.setDescription("Some description");
		
		Homework homework = TstDataUtils.createHomework(TstDataUtils.createStudent("jd"), TstDataUtils.createModule());
		topicProgress = new TopicProgress(topic, homework);
		
		adapter = new TopicProgressAdapter(topicProgress);
	}
	
	@Test
	public void verifyThatDescriptionIsCopiedIfDetailedInfo() {
		// Given		
		TopicDetailedProgressInfo info = new TopicDetailedProgressInfo();
		
		// When
		adapter.populate(info);
		
		// Then
		Assert.assertThat(info.getDescription(), CoreMatchers.is("Some description"));
	}
}
