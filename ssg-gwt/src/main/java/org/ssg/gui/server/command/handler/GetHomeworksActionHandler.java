package org.ssg.gui.server.command.handler;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.StudentService;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.server.command.ActionHandler;

@Service
public class GetHomeworksActionHandler implements ActionHandler<GetHomeworksResponse, GetHomeworks> {

	@Autowired
	private StudentService studentService;

	public GetHomeworksResponse execute(GetHomeworks action) throws SsgGuiServiceException {
		Collection<HomeworkInfo> homeworks = studentService.getHomeworks(action.getStudentId());
		return new GetHomeworksResponse(new ArrayList<HomeworkInfo>(homeworks));
	}

	public Class<GetHomeworks> forClass() {
		return GetHomeworks.class;
	}

}
