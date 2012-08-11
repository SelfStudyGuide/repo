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

@Service
public class GetTopicInfoActionHandler implements ActionHandler<GetTopicInfoResponse, GetTopicInfo> {

	private static final Log LOG = LogFactory.getLog(GetTopicInfoActionHandler.class);

	@Autowired
	private HomeworkDao homeworkDao;

	@Transactional
	public GetTopicInfoResponse execute(GetTopicInfo action) throws SsgGuiServiceException {

		LOG.debug("Running GetTopicInfoActionHandler action with " + action);

		int topicId = action.getTopicId();
		int homeworkId = action.getHomeworkId();
		Homework homework = loadHomework(homeworkId);
		TopicProgress topicProgress = loadTopic(topicId, homework);

		TopicDetailedProgressInfo info = new TopicDetailedProgressInfo();
		new TopicProgressAdapter(topicProgress).populate(info);
		return new GetTopicInfoResponse(info);
	}

	private TopicProgress loadTopic(int topicId, Homework homework) {
		TopicProgress topicProgress = homework.getTopicProgress(topicId);

		if (topicProgress == null) {
			throw new SsgGuiServiceException("TopicProgress object cannot be found in db with id " + topicId,
			        "topic.view.notfound");
		}
		return topicProgress;
	}

	private Homework loadHomework(int homeworkId) {
		Homework homework = homeworkDao.getHomework(homeworkId);

		if (homework == null) {
			throw new SsgGuiServiceException("Homework object cannot be found in db with id " + homeworkId,
			        "topic.view.notfound");
		}
		return homework;
	}

	public Class<GetTopicInfo> forClass() {
		return GetTopicInfo.class;
	}

}
