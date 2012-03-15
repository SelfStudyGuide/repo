package org.ssg.gui.client.studenthome;

import com.google.gwt.i18n.client.Messages;

public interface StudentHomeworkMessages extends Messages {
	@Key("view.title")
	String viewTitle();

	@Key("homework.table.id")
	String homeworkTableId();

	@Key("homework.table.module")
	String homeworkTableModule();

	@Key("homework.table.status")
	String homeworkTableStatus();

	@Key("service.error.unexpected")
	String serviceErrorUnexpected(String error);

}
