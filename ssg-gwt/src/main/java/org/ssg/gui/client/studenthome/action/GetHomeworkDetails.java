package org.ssg.gui.client.studenthome.action;

import java.io.Serializable;

import org.ssg.gui.client.action.BaseAction;

public class GetHomeworkDetails extends BaseAction<GetHomeworkDetailsResponse> implements Serializable {
	private static final long serialVersionUID = 5012787608118563550L;

	private int homeworkId;
	private int studentId;

	protected GetHomeworkDetails() {
	}

	public GetHomeworkDetails(int studentId, int homeworkId) {
		this.studentId = studentId;
		this.homeworkId = homeworkId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

}
