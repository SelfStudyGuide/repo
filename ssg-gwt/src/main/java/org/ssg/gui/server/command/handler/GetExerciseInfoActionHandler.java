package org.ssg.gui.server.command.handler;

import ma.glasnost.orika.MapperFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Homework;
import org.ssg.core.dto.ExerciseInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.task.action.GetExerciseInfo;
import org.ssg.gui.client.task.action.GetExerciseInfoResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.ActionHandlerUtils;
import org.ssg.gui.server.command.datamap.DataMappingSupport;
import org.ssg.gui.server.command.datamap.OrikaDataMappingConfiguration;

@Service
@Transactional
public class GetExerciseInfoActionHandler implements ActionHandler<GetExerciseInfoResponse, GetExerciseInfo>,
        InitializingBean {

	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired(required = true)
	@Qualifier("OricaDataMapping")
	private DataMappingSupport mapper;

	public GetExerciseInfoResponse execute(GetExerciseInfo action) throws SsgGuiServiceException {

		ExerciseInfo result = new ExerciseInfo();

		Exercise exercise = curriculumDao.getExercise(action.getExerciseId());

		ActionHandlerUtils.assertObjectNotNull(exercise, "task.exercise.notfound",
		        "Exercise object not found by id: %s", action.getExerciseId());

		result = mapper.map(exercise, result);

		Homework homework = homeworkDao.getHomework(action.getHomeworkId());

		ActionHandlerUtils.assertObjectNotNull(homework, "task.homework.notfound",
		        "Homework object not found by id: %s", action.getHomeworkId());

		if (!homework.hasExercise(exercise)) {
			throw new SsgGuiServiceException(String.format("Exercise %s does not belong to homework %s",
			        exercise.getId(), homework.getId()), "task.exercise.dosnt.belong.to.homework");
		}

		result = mapper.map(homework, result);

		return new GetExerciseInfoResponse(result);
	}

	public Class<GetExerciseInfo> forClass() {
		return GetExerciseInfo.class;
	}

	public void afterPropertiesSet() throws Exception {
		mapper.setDataMappingConfiguration(new OrikaDataMappingConfiguration() {

			public void copfigureDataMapper(MapperFactory factory) {
				factory.registerClassMap(factory.classMap(Homework.class, ExerciseInfo.class).field("id", "homeworkId")
				        .byDefault().toClassMap());
			}
		});
	}

}
