package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.domain.Module;
import org.ssg.core.domain.Topic;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

@ContextConfiguration(locations = { "/test-config.ctx.xml", "/spring/core-service.ctx.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TopicDaoIntegrationTest extends AbstractDaoTestSupport {


	@Test
	public void verifyThatModuleAndTopicCanBeSavedInCascade() {
		saveModuleWithTopic();

		clearSession();

		Topic savedTopic = getSavedTopic();
		Assert.assertThat(savedTopic.getId(), is(not(0)));
	}

	@Test
	public void verifyThatNewTopicCanBeAsignedWithExistingModule() {
		Module module = TstDataUtils.createModule();
		module.setName("some unique name " + System.currentTimeMillis());
		curriculumDao.saveModule(module);

		clearSession();

		Topic topic = createTestTopic("test topic 1");
		String topicName = topic.getName();
		addTopicToModule(module, topic, new ArrayList<Topic>());

		curriculumDao.saveTopic(topic);

		clearSession();

		Topic savedTopic = getSavedTopic();
		Assert.assertThat(savedTopic.getId(), is(not(0)));
		Assert.assertThat(savedTopic.getName(), equalTo(topicName));

	}

	@Test
	// @Rollback(value = false)
	public void verifyThatTopicCanBeAddedToPercictedModuleWhereOtherTopicExists() {
		saveModuleWithTopic();
		clearSession();

		Module savedModule = getSavedModule();

		Topic topic = createTestTopic("test topic 2");
		addTopicToModule(savedModule, topic, new ArrayList<Topic>());

		curriculumDao.saveTopic(topic);

		clearSession();

		savedModule = getSavedModule();

		List<Topic> savedTopics = savedModule.getTopics();
		Assert.assertThat("Expected two topics have been saved",
				savedTopics.size(), is(2));
	}

	@Test
	public void verifyThatTopicCanBeAddedToPercictedModule() {
		Module module = TstDataUtils.createModuleWithUniqueName();
		curriculumDao.saveModule(module);
		Module savedModule = getSavedModule();

		Topic t = createTestTopic("test topic descr");
		addTopicToModule(savedModule, t, new ArrayList<Topic>());
		Topic savedTopic = getSavedTopic();

		Assert.assertThat(savedTopic.getId(), is(not(0)));
	}

	@Test
	@Ignore
	public void verifyThatSavedTopicCanByFound() {
		saveModuleWithTopic();

		Module savedModule = getSavedModule();

		List<Topic> topics = curriculumDao.getTopicsByModuleId(savedModule
				.getId());
		Assert.assertThat("Expected one topic has been saved", topics.size(),
				is(1));
	}

	private void addTopicToModule(Module savedModule, Topic topic,
			List<Topic> topics) {
		topics.add(topic);
		savedModule.setTopics(topics);
		topic.setModule(savedModule);
	}

	private void saveModuleWithTopic() {
		Module module = TstDataUtils.createModuleWithUniqueName();
		Topic topic = createTestTopic("test topic descr");
		addTopicToModule(module, topic, new ArrayList<Topic>());
		curriculumDao.saveModule(module);
	}

	private Topic createTestTopic(String desc) {
		Topic topic = TstDataUtils.createTopicWithUniqueName();
		topic.setDescription(desc);
		return topic;
	}

	@Override
	protected void cleanUpDb() {
		deleteAll(Topic.class);
		deleteAll(Module.class);
	}

	
}
