package org.ssg.gui.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.ActionHandlerLookup;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

@Service
public class StudentUIServiceImpl implements StudentUIService {
	private static final long serialVersionUID = -4678144429318786071L;
	
	@Autowired
	private ActionHandlerLookup handlerLookup; 
	
	public <R extends Response> R execute(Action<R> action) {
		ActionHandler<R, Action<R>> handler = handlerLookup.findHandler(action.getClass());
		return handler.execute(action);
	}

}
