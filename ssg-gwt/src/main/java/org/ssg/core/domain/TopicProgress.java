package org.ssg.core.domain;

import java.util.List;

import javax.persistence.Transient;

public class TopicProgress {
	private int id;
	private Homework homework;
	private Topic topic;	
	private String status;
	private List<Answer> answers;
	
	
	public TopicProgress(Topic topic) {
		this.topic = topic;		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Transient
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public String getTopicName() {
		return getTopic() != null ? getTopic().getName() : null;
	}
	
}
