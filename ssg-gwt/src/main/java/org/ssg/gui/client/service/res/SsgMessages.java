package org.ssg.gui.client.service.res;

import com.google.gwt.i18n.client.Messages;

/**
 * All messages across whole application should be registered here.
 * <br>
 * SsgMessages.properties
 */
public interface SsgMessages extends Messages {
	@Key("topic.view.topic.status.inprogress")
	String topicViewTopicStatusInProgress();
	
	@Key("topic.view.topic.status.notstarted")
	String topicViewTopicStatusNosStarted();
	
	@Key("topic.view.topic.status.done")
	String topicViewTopicStatusDone();
	
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
	
	@Key("homework.details.view.title")
	String homeworkDetailsViewTitle();
	
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
	
	@Key("topic.view.task.text")
	String topicViewTaskText();
	
	@Key("topic.view.task.listening")
	String topicViewTaskListening();
	
	@Key("topic.view.task.lexical")
	String topicViewTaskLexical();
	
	@Key("topic.view.task.title")
	String topicViewTaskTitle();
}
