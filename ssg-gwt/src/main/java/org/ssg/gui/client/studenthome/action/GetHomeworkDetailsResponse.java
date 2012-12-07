package org.ssg.gui.client.studenthome.action;

import java.io.Serializable;

import org.ssg.core.dto.HomeworkDetailedInfo;
import org.ssg.gui.client.action.Response;

public class GetHomeworkDetailsResponse implements Response, Serializable {
	private static final long serialVersionUID = 9010519578060555721L;

	private HomeworkDetailedInfo homework;

	/**
	 * For GWT RPC usage
	 * */
	public GetHomeworkDetailsResponse() {
	}

	public GetHomeworkDetailsResponse(HomeworkDetailedInfo homework) {
		this.homework = homework;
	}

	public HomeworkDetailedInfo getHomework() {
		return homework;
	}
}
