package org.ssg.gui.client.studenthome.action;

import java.io.Serializable;

import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.action.Response;

public class GetHomeworkDetailsResponse implements Response, Serializable {
	private static final long serialVersionUID = 9010519578060555721L;

	private HomeworkInfo homework;

	/**
	 * For GWT RPC usage
	 * */
	protected GetHomeworkDetailsResponse() {
	}
	
	public GetHomeworkDetailsResponse(HomeworkInfo homework) {
		this.homework = homework;
	}

	public HomeworkInfo getHomework() {
		return homework;
	}

	public void setHomework(HomeworkInfo homework) {
		this.homework = homework;
	}
}
