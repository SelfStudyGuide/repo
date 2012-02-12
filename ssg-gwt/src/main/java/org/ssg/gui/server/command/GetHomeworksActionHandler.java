package org.ssg.gui.server.command;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.StudentService;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;

@Service
public class GetHomeworksActionHandler implements
		ActionHandler<GetHomeworksResponse, GetHomeworks> {

	@Autowired
	private StudentService studentService;

	public GetHomeworksResponse execute(GetHomeworks action) {
		Collection<HomeworkInfo> homeworks = studentService.getHomeworks(action
				.getStudentId());
		return new GetHomeworksResponse(new ArrayList<HomeworkInfo>(homeworks));
	}

	public Class<GetHomeworks> forClass() {
		return GetHomeworks.class;
	}

}
