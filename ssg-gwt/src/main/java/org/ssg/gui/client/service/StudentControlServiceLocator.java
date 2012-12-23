package org.ssg.gui.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class StudentControlServiceLocator {

	public static StudentControlServiceAsync create() {

		StudentControlServiceAsync studentService = GWT.create(StudentControlService.class);
		if (studentService instanceof ServiceDefTarget) {
			((ServiceDefTarget) studentService)
			        .setServiceEntryPoint(GWT.getHostPageBaseURL() + "StudentControlService");
		}
		return studentService;
	}
}
