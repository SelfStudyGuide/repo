package org.ssg.gui.client.service;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

//@RemoteServiceRelativePath("Student")
public interface StudentControlService extends RemoteService {
	<T extends Response> T execute(Action<T> action) throws SsgGuiServiceException;
}
