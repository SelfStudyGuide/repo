package org.ssg.gui.server.command.handler;

import ma.glasnost.orika.MapperFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.ActionHandlerUtils;
import org.ssg.gui.server.command.datamap.DataMappingSupport;
import org.ssg.gui.server.command.datamap.OrikaDataMappingConfiguration;
import org.ssg.gui.server.security.Authorization;
import org.ssg.gui.server.security.CommandPreAuthorize;
import org.ssg.gui.server.security.SsgSecurityException;

@Service
public class GetTopicInfoActionHandler implements ActionHandler<GetTopicInfoResponse, GetTopicInfo>, CommandPreAuthorize<GetTopicInfo>, InitializingBean {

	private static final Log LOG = LogFactory.getLog(GetTopicInfoActionHandler.class);

	@Autowired
	private HomeworkDao homeworkDao;
	
	@Autowired
	private CurriculumDao curriculumDao;
	
	@Autowired
	private Authorization authorization;
	
	@Autowired(required = true)
	@Qualifier("OricaDataMapping")
	private DataMappingSupport mapper;

	@Transactional
	public GetTopicInfoResponse execute(GetTopicInfo action) throws SsgGuiServiceException {

		LOG.debug("Running GetTopicInfoActionHandler action with " + action);

		int topicId = action.getTopicId();
		int homeworkId = action.getHomeworkId();

		Homework homework = loadHomework(homeworkId);

		TopicProgress topicProgress = homework.getTopicProgress(topicId);
		ActionHandlerUtils.assertObjectNotNull(topicProgress, "topic.view.notfound",
		        "TopicProgress object cannot be found in db with id: %s", topicId);

		Topic topic = curriculumDao.getTopic(topicId);

		TopicDetailedProgressInfo info = new TopicDetailedProgressInfo();
		mapper.map(topicProgress, info);
		mapper.map(topic, info);

		return new GetTopicInfoResponse(info);
	}

	private Homework loadHomework(int homeworkId) {
		Homework homework = homeworkDao.getHomework(homeworkId);
		ActionHandlerUtils.assertObjectNotNull(homework, "topic.view.notfound",
		        "Homework object cannot be found in db with id: %s ", homeworkId);
		return homework;
	}

	public Class<GetTopicInfo> forClass() {
		return GetTopicInfo.class;
	}

	public void preAuthorize(GetTopicInfo action) throws SsgSecurityException {
		authorization.ownHomework(loadHomework(action.getHomeworkId()));
	}

	public void afterPropertiesSet() throws Exception {
		mapper.setDataMappingConfiguration(new OrikaDataMappingConfiguration() {

			public void copfigureDataMapper(MapperFactory factory) {
				factory.registerClassMap(factory.classMap(Topic.class, TopicDetailedProgressInfo.class)
				        .field("name", "topicName").byDefault().toClassMap());
			}
		});
	}

}
