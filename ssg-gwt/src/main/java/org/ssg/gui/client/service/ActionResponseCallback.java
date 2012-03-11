package org.ssg.gui.client.service;

import org.ssg.gui.shared.Response;

public interface ActionResponseCallback<R extends Response> {
	void onResponse(R response);
	void onError(SsgGuiServiceException exception);
}
