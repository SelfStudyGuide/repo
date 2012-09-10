package org.ssg.gui.client.task;

import org.ssg.gui.client.service.BaseEntryPoint;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;

public class TaskEntryPoint extends BaseEntryPoint {

	@Override
	protected void initPageContent() {
		getHandlerManager().fireEvent(new RetrieveUserInfoEvent());
	}

}
