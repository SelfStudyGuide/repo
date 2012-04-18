package org.ssg.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TOPIC_PROGRESS")
public class TopicProgress implements Serializable {
	private static final long serialVersionUID = -1107116198666337327L;
	//private int id;
	private Homework homework;
	private Topic topic;	
	private String status;
	private List<Answer> answers;
	
	
	public TopicProgress(Topic topic, Homework homework) {
		this.topic = topic;
		this.homework = homework;		
	}
	
	public TopicProgress() {
		
	}
	
//	@Transient
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HW_ID")
	public Homework getHomework() {
		return homework;
	}

	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOPIC_ID")
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Column(name = "STATUS")
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
	
	@Transient
	public String getTopicName() {
		return getTopic() != null ? getTopic().getName() : null;
	}
	
	@Transient
	public int getTopicId() {
		return getTopic() != null ? getTopic().getId() : -1;
	}
	
}
