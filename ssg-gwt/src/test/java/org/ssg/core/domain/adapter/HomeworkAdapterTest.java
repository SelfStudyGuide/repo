package org.ssg.core.domain.adapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.dto.TopicProgressInfo;
import org.ssg.core.support.TstDataUtils;

public class HomeworkAdapterTest {

	private Homework homework;

	@Before
	public void setUp() {
		Student student = TstDataUtils.createStudent("studen with homework");
		Module module = TstDataUtils.enrichModuleWithTopics(TstDataUtils.createModule());
		module.setName("Testing Module");
		homework = TstDataUtils.createHomework(student, module);
		homework.setId(2);
		homework = TstDataUtils.enrichHomeworkWithProgress(homework, module.getTopics());
		homework.getProgresses().get(0).setStatus("0");
		homework.getProgresses().get(1).setStatus("20");
		homework.getProgresses().get(2).setStatus("100");
	}

	@Test
	public void verifyThatIdFieldArePopulated() {
		HomeworkAdapter adapter = new HomeworkAdapter(homework, true);

		HomeworkInfo info = populateInfoAndReturn(adapter);

		assertThat(info.getId(), is(2));
	}

	@Test
	public void verifyThatAssignedModulesFieldArePopulated() {
		HomeworkAdapter adapter = new HomeworkAdapter(homework, false);

		HomeworkInfo info = populateInfoAndReturn(adapter);

		assertThat(info.getAssignedModules(),
				equalTo("Testing Module"));
	}

	@Test
	public void verifyThatAssignedModulesFieldArePopulatedEmptyIfNoModules() {
		homework.setModules(null);
		HomeworkAdapter adapter = new HomeworkAdapter(homework, true);

		HomeworkInfo info = populateInfoAndReturn(adapter);

		assertThat(info.getAssignedModules(), equalTo(""));
	}
	
	@Test
	public void verifyThatTopicProgressCollectionIsPopulatedIfDetails() {
		HomeworkAdapter adapter = new HomeworkAdapter(homework, true);
		HomeworkInfo info = populateInfoAndReturn(adapter);
		
		TopicProgressInfo topicProgressInfo = info.getTopicProgress().get(0);
		assertThat(topicProgressInfo.getId(), equalTo(11));
		assertThat(topicProgressInfo.getTopicName(), equalTo("Test topic 1"));
		assertThat(topicProgressInfo.getStatus(), equalTo("0"));
		
		topicProgressInfo = info.getTopicProgress().get(1);
		assertThat(topicProgressInfo.getId(), equalTo(12));
		assertThat(topicProgressInfo.getTopicName(), equalTo("Test topic 2"));
		assertThat(topicProgressInfo.getStatus(), equalTo("20"));
		
		topicProgressInfo = info.getTopicProgress().get(2);
		assertThat(topicProgressInfo.getId(), equalTo(13));
		assertThat(topicProgressInfo.getTopicName(), equalTo("Test topic 3"));
		assertThat(topicProgressInfo.getStatus(), equalTo("100"));
		
	}
	
	@Test
	public void verifyThatTopicProgressCollectionIsNotPopulatedIfNoDetails() {
		HomeworkAdapter adapter = new HomeworkAdapter(homework, false);
		HomeworkInfo info = populateInfoAndReturn(adapter);
		
		assertThat(info.getTopicProgress().size(), is(0));
	}
		
	
	
	private HomeworkInfo populateInfoAndReturn(HomeworkAdapter adapter) {
		HomeworkInfo info = new HomeworkInfo();
		adapter.populate(info);
		return info;
	}
}
