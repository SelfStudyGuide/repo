package org.ssg.gui.client.studenthome;

import com.google.gwt.i18n.client.Messages;

// StudentHomeworkMessages.properties
public interface StudentHomeworkMessages extends Messages {
	@Key("homework.view.title")
	String viewTitle();

	@Key("homework.table.id")
	String homeworkTableId();

	@Key("homework.table.module")
	String homeworkTableModule();

	@Key("homework.table.status")
	String homeworkTableStatus();
	
	@Key("homework.table.openbutton")
	String homeworkTableOpenButton();

	@Key("homework.btn.refresh")
	String homeworkRefreshButton();
	
	@Key("service.error.unexpected")
	String serviceErrorUnexpected(String error);
	
	@Key("homework.details.notstarted")
	String homeworkDetailsNotStarted(String topicName);

	@Key("homework.details.inprogress")
	String homeworkDetailsInProgress(String topicName);

	@Key("homework.details.done")
	String homeworkDetailsDone(String topicName);

	@Key("homework.details.assigned")
	String homeworkDetailsAssigned();
	
	@Key("homework.details.complete")
	String homeworkDetailsComplete();
	
	@Key("homework.details.teacher")
	String homeworkDetailsTeacher();
	
}
