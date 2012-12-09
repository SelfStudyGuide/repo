package org.ssg.gui.server.command.handler;

import static org.ssg.gui.server.command.ActionHandlerUtils.assertObjectNotNull;
import ma.glasnost.orika.MapperFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.HomeworkDetailedInfo;
import org.ssg.core.dto.TopicProgressInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.datamap.DataMappingSupport;
import org.ssg.gui.server.command.datamap.OrikaDataMappingConfiguration;

@Service
@Transactional
public class GetHomeworkDetailsActionHandler implements ActionHandler<GetHomeworkDetailsResponse, GetHomeworkDetails>,
        InitializingBean {

	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired(required = true)
	@Qualifier("OricaDataMapping")
	private DataMappingSupport mapper;

	public GetHomeworkDetailsResponse execute(GetHomeworkDetails action) throws SsgGuiServiceException {

		HomeworkDetailedInfo homeworkInfo = new HomeworkDetailedInfo();

		Homework homework = homeworkDao.getHomework(action.getHomeworkId());
		assertObjectNotNull(homework, "studenthome.homework.not.found", "Homework with id %s not found",
		        action.getHomeworkId());

		mapper.map(homework, homeworkInfo);
		Integer moduleId = homework.getModuleId();
		Module module = curriculumDao.getModuleById(moduleId);

		assertObjectNotNull(module, "studenthome.module.not.found", "Module not found in DB with id", moduleId);
		mapper.map(module, homeworkInfo);

		return new GetHomeworkDetailsResponse(homeworkInfo);
	}

	public Class<GetHomeworkDetails> forClass() {
		return GetHomeworkDetails.class;
	}

	public void afterPropertiesSet() throws Exception {
		mapper.setDataMappingConfiguration(new OrikaDataMappingConfiguration() {

			public void copfigureDataMapper(MapperFactory factory) {
				factory.registerClassMap(factory.classMap(TopicProgress.class, TopicProgressInfo.class).byDefault()
				        .toClassMap());
				factory.registerClassMap(factory.classMap(Homework.class, HomeworkDetailedInfo.class)
				        .field("progresses", "topicProgress").byDefault().toClassMap());
				factory.registerClassMap(factory.classMap(Module.class, HomeworkDetailedInfo.class)
				        .field("name", "assignedModules").toClassMap());
			}
		});
	}

}
