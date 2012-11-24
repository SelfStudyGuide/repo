package org.ssg.gui.server.command.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.domain.adapter.TopicProgressAdapter;
import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.ActionHandlerUtils;
import org.ssg.gui.server.security.Authorization;
import org.ssg.gui.server.security.CommandPreAuthorize;
import org.ssg.gui.server.security.SsgSecurityException;

@Service
public class GetTopicInfoActionHandler implements ActionHandler<GetTopicInfoResponse, GetTopicInfo>, CommandPreAuthorize<GetTopicInfo> {

	private static final Log LOG = LogFactory.getLog(GetTopicInfoActionHandler.class);

	@Autowired
	private HomeworkDao homeworkDao;
	
	@Autowired
	private Authorization authorization;

	@Transactional
	public GetTopicInfoResponse execute(GetTopicInfo action) throws SsgGuiServiceException {

		LOG.debug("Running GetTopicInfoActionHandler action with " + action);

		int topicId = action.getTopicId();
		int homeworkId = action.getHomeworkId();
		
		Homework homework = loadHomework(homeworkId);
		
		TopicProgress topicProgress = homework.getTopicProgress(topicId);
		ActionHandlerUtils.assertObjectNotNull(topicProgress, "topic.view.notfound", "TopicProgress object cannot be found in db with id: %s", topicId);

		TopicDetailedProgressInfo info = new TopicDetailedProgressInfo();
		new TopicProgressAdapter(topicProgress).populate(info);
		
		return new GetTopicInfoResponse(info);
	}

	private Homework loadHomework(int homeworkId) {
		Homework homework = homeworkDao.getHomework(homeworkId);		
		ActionHandlerUtils.assertObjectNotNull(homework, "topic.view.notfound", "Homework object cannot be found in db with id: %s ", homeworkId);
		return homework;
	}

	public Class<GetTopicInfo> forClass() {
		return GetTopicInfo.class;
	}

	public void preAuthorize(GetTopicInfo action) throws SsgSecurityException {
		authorization.ownHomework(loadHomework(action.getHomeworkId()));
	}

}
