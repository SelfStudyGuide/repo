package org.ssg.gui.server.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssg.core.service.StudentService;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;
import org.ssg.gui.server.command.ActionHandler;

@Service
public class GetHomeworkDetailsActionHandler implements ActionHandler<GetHomeworkDetailsResponse, GetHomeworkDetails> {

	@Autowired
	private StudentService service;

	public GetHomeworkDetailsResponse execute(GetHomeworkDetails action) throws SsgGuiServiceException {
		return new GetHomeworkDetailsResponse(service.getHomeworksDetails(action.getHomeworkId()));
	}

	public Class<GetHomeworkDetails> forClass() {
		return GetHomeworkDetails.class;
	}

}
