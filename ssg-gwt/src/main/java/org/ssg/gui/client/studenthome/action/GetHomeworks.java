package org.ssg.gui.client.studenthome.action;

import java.io.Serializable;

import org.ssg.gui.shared.Action;

public class GetHomeworks implements Action<GetHomeworksResponse>, Serializable {
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
