package org.ssg.gui.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.gui.client.service.StudentControlService;
import org.ssg.gui.server.SpringGwtServlet;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public class StudentControlServiceImpl extends SpringGwtServlet implements
		StudentControlService {

	private static final long serialVersionUID = 992653253296938793L;

	@Autowired
	private StudentUIService studentUIService;

	public <T extends Response> T execute(Action<T> action) {
		return studentUIService.execute(action);
	}

}
