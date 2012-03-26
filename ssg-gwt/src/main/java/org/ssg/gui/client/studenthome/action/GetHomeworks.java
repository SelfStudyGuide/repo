package org.ssg.gui.client.studenthome.action;

import org.ssg.gui.client.action.BaseAction;


public class GetHomeworks extends BaseAction<GetHomeworksResponse> {
	private static final long serialVersionUID = 657375126825040612L;

	private int studentId;

	public GetHomeworks() {

	}

	public GetHomeworks(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

}
