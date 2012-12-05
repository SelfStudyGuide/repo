package org.ssg.gui.server.command.handler;

import static org.ssg.gui.server.command.ActionHandlerUtils.assertObjectNotNull;

import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.UserDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.datamap.DataMappingSupport;
import org.ssg.gui.server.command.datamap.OrikaDataMappingConfiguration;

@Service
@Transactional
public class GetHomeworksActionHandler implements ActionHandler<GetHomeworksResponse, GetHomeworks>, InitializingBean {

	private static final Log LOG = LogFactory.getLog(GetHomeworksActionHandler.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired(required = true)
	@Qualifier("OricaDataMapping")
	private DataMappingSupport mapper;

	public GetHomeworksResponse execute(GetHomeworks action) throws SsgGuiServiceException {

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Executing action %s", action));
		}
		ArrayList<HomeworkInfo> result = new ArrayList<HomeworkInfo>();

		Student student = userDao.getStudentById(action.getStudentId());

		assertObjectNotNull(student, "studenthome.student.not.found", "Student %s is not found in D",
		        action.getStudentId());

		List<Homework> homeworks = student.getHomeworks();

		for (Homework homework : homeworks) {
			HomeworkInfo homeworkInfo = new HomeworkInfo();
			doMapping(homework, homeworkInfo);			
			result.add(homeworkInfo);
		}

		return new GetHomeworksResponse(result);

	}

	private void doMapping(Homework homework, HomeworkInfo homeworkInfo) {
	    mapper.map(homework, homeworkInfo);
	    
	    Integer moduleId = homework.getModuleId();
	    Module module = curriculumDao.getModuleById(moduleId);
	    
	    assertObjectNotNull(module, "studenthome.module.not.found", "Module not found by id %s", moduleId);
	    
	    mapper.map(module, homeworkInfo);
    }

	public Class<GetHomeworks> forClass() {
		return GetHomeworks.class;
	}

	public void afterPropertiesSet() throws Exception {
		mapper.setDataMappingConfiguration(new OrikaDataMappingConfiguration() {

			public void copfigureDataMapper(MapperFactory factory) {
				factory.registerClassMap(factory.classMap(Homework.class, HomeworkInfo.class).byDefault().toClassMap());
				factory.registerClassMap(factory.classMap(Module.class, HomeworkInfo.class)
				        .field("name", "assignedModules").toClassMap());
			}
		});
	}

}
