package org.ssg.gui.server.command.handler;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.Type;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Task;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.datamap.DataMappingSupport;
import org.ssg.gui.server.command.datamap.OrikaDataMappingConfiguration;
import org.ssg.gui.server.security.Authorization;
import org.ssg.gui.server.security.CommandPreAuthorize;
import org.ssg.gui.server.security.SsgSecurityException;

@Service
public class GetTaskInfoActionHandler implements ActionHandler<GetTaskInfoResponse, GetTaskInfo>, InitializingBean, CommandPreAuthorize<GetTaskInfo> {

	@Autowired(required = true)
	@Qualifier("OricaDataMapping")
	private DataMappingSupport mapper;

	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired
	private HomeworkDao homeworkDao;
	
	@Autowired
	private Authorization authorization;

	public Class<GetTaskInfo> forClass() {
		return GetTaskInfo.class;
	}

	@Transactional
	public GetTaskInfoResponse execute(GetTaskInfo action) throws SsgGuiServiceException {

		Task task = curriculumDao.getTask(action.getTaskId());

		if (task != null) {
			
			int topicId = task.getTopic().getId();
			Homework homework = homeworkDao.getHomework(action.getHomeworkId());

			if (homework != null) {
				if (homework.hasTopic(topicId)) {
					TopicTaskInfo taskInfo = mapper.map(task, new TopicTaskInfo());
					return new GetTaskInfoResponse(taskInfo);
				} else {
					throw new SsgGuiServiceException("Topic " + topicId + " does not belong to homework "
					        + homework.getId(), "task.task.does.not.belong.to.hw");
				}
			} else {
				throw new SsgGuiServiceException("Homework " + action.getHomeworkId() + " has not been found ",
				        "task.homework.notfound");
			}
		} else {
			throw new SsgGuiServiceException("Task " + action.getTaskId() + " is not found", "task.task.notfound");
		}
	}

	private static class DataMapperConfiguration implements OrikaDataMappingConfiguration {

		public void copfigureDataMapper(MapperFactory factory) {

			factory.getConverterFactory().registerConverter(new CustomConverter<Exercise, Integer>() {
				public Integer convert(Exercise source, Type<? extends Integer> destinationType) {
					return Integer.valueOf(source.getId());
				}
			});

			factory.registerClassMap(factory.classMap(Task.class, TopicTaskInfo.class)
			        .field("exercises", "exerciseIds").byDefault().toClassMap());
		}
	}

	public void afterPropertiesSet() throws Exception {
		mapper.setDataMappingConfiguration(new DataMapperConfiguration());
	}

	public void preAuthorize(GetTaskInfo action) throws SsgSecurityException, SsgGuiServiceException {
		Homework homework = homeworkDao.getHomework(action.getHomeworkId());
		if (homework != null) {
			authorization.ownHomework(homework);
		}
    }

}
