package org.ssg.gui.server.command.handler;

import static org.ssg.gui.server.command.ActionHandlerLogHelper.debug;
import static org.ssg.gui.server.command.ActionHandlerLogHelper.info;

import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.topic.action.GetTopicTasksInfo;
import org.ssg.gui.client.topic.action.GetTopicTasksInfoResponse;
import org.ssg.gui.server.command.AbstractMapperActionHandler;
import org.ssg.gui.server.command.datamap.OrikaDataMappingConfiguration;

@Service
public class GetTopicTasksInfoActionHandler
		extends
		AbstractMapperActionHandler<GetTopicTasksInfoResponse, GetTopicTasksInfo>
		implements InitializingBean {

	private static final Log LOG = LogFactory.getLog(GetTopicTasksInfoActionHandler.class);
	
	@Autowired
	private HomeworkDao homeworkDao;

	@Transactional
	public GetTopicTasksInfoResponse execute(GetTopicTasksInfo action)
			throws SsgGuiServiceException {

		Homework homework = loadHomework(action.getHomeworkId());
		TopicProgress topicProgress = loadTopic(action.getTopicId(), homework);
		
		List<TopicTaskInfo> taskInfos = new ArrayList<TopicTaskInfo>();

		List<Task> tasks = topicProgress.getTopic().getTasks();
		
		debug(LOG, action, "Loaded %s task(s) for topic %d", tasks.size(),
				action.getTopicId());

		for (Task task : tasks) {
			TopicTaskInfo taskInfo = map(task, new TopicTaskInfo());
			taskInfos.add(taskInfo);
			
			info(LOG, action,
					"Added TaskInfo %s for topic %d", taskInfo.getType(),
					action.getTopicId());
			
		}

		return new GetTopicTasksInfoResponse(taskInfos);
	}
	
	private TopicProgress loadTopic(int topicId, Homework homework) {
		TopicProgress topicProgress = homework.getTopicProgress(topicId);
		
		if (topicProgress == null) {
			throw new SsgGuiServiceException(
					"TopicProgress object cannot be found in db with id "
							+ topicId, "topic.view.notfound");
		}
		return topicProgress;
	}

	private Homework loadHomework(int homeworkId) {
		Homework homework = homeworkDao.getHomework(homeworkId);
		
		if (homework == null) {
			throw new SsgGuiServiceException(
					"Homework object cannot be found in db with id "
							+ homeworkId, "topic.view.notfound");
		}
		return homework;
	}

	public Class<GetTopicTasksInfo> forClass() {
		return GetTopicTasksInfo.class;
	}

	private static class DataMapperConfiguration implements
			OrikaDataMappingConfiguration {

		public void copfigureDataMapper(MapperFactory factory) {
			ClassMapBuilder<Task, TopicTaskInfo> mapBuilder = ClassMapBuilder
					.map(Task.class, TopicTaskInfo.class);

			factory.registerClassMap(mapBuilder.byDefault().toClassMap());
		}
	}

	public void afterPropertiesSet() throws Exception {
		setDataMappingConfiguration(new DataMapperConfiguration());
	}

}
