package org.ssg.gui.client.service;

import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Student")
public interface StudentControlService extends RemoteService {
	<T extends Response> T execute(Action<T> action) throws SsgGuiServiceException;
}
