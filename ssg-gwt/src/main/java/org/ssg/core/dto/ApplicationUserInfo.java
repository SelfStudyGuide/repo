package org.ssg.core.dto;

import java.io.Serializable;

public class ApplicationUserInfo implements Serializable {
	private static final long serialVersionUID = -61938951539407057L;

	private String username;
	private int studentId;
	private int teacherId;

	public String getUsername() {
		return username;
	}

	public int getStudentId() {
		return studentId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

}
