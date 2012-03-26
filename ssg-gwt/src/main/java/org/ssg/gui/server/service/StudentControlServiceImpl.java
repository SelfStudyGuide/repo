package org.ssg.gui.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.StudentControlService;
import org.ssg.gui.server.SpringGwtServlet;

public class StudentControlServiceImpl extends SpringGwtServlet implements
		StudentControlService {
	
	private static final long serialVersionUID = 992653253296938793L;

	@Autowired
	private StudentUIService studentUIService;

	public <T extends Response> T execute(Action<T> action) throws SsgGuiServiceException {
		return studentUIService.execute(action);
	}

}
