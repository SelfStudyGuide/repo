package org.ssg.gui.server.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.StudentControlService;
import org.ssg.gui.server.SpringGwtServlet;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public class StudentControlServiceImpl extends SpringGwtServlet implements
		StudentControlService {

	private static final Log LOG = LogFactory.getLog(StudentControlServiceImpl.class);
	
	private static final long serialVersionUID = 992653253296938793L;

	@Autowired
	private StudentUIService studentUIService;

	public <T extends Response> T execute(Action<T> action) throws SsgGuiServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Executing action: " + action.getActionName());
		}
		return studentUIService.execute(action);
	}

}
