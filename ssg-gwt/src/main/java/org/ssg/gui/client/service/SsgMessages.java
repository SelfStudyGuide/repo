package org.ssg.gui.client.service;

import com.google.gwt.i18n.client.Messages;

// SsgMessages.properties
public interface SsgMessages extends Messages {
	@Key("topic.view.topic.status.inprogress")
	String topicViewTopicStatusInProgress();
	
	@Key("topic.view.topic.status.notstarted")
	String topicViewTopicStatusNosStarted();
	
	@Key("topic.view.topic.status.done")
	String topicViewTopicStatusDone();
}
