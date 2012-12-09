package org.ssg.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Should aggregate all information regarding to progress of topic.
 * 
 * Exercise should have completion status NOT_STARTED, IN_PROGRESS, DONE
 */

@Entity
@Table(name = "TOPIC_PROGRESS")
public class TopicProgress implements Serializable {
	private static final long serialVersionUID = -1107116198666337327L;
	private Homework homework;
	
	private String status;
	// Not sure that answers is neede here.
	//private List<Answer> answers;
	private int topicId;
	private String topicName;

	public TopicProgress(Topic topic, Homework homework) {
		this.topicId = topic.getId();
		this.topicName = topic.getName();
		this.homework = homework;
	}

	public TopicProgress() {

	}

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
	@Column(name = "TOPIC_ID")
	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	@Transient
//	public List<Answer> getAnswers() {
//		return answers;
//	}
//
//	public void setAnswers(List<Answer> answers) {
//		this.answers = answers;
//	}

	@Column(name = "TOPIC_NAME")
	public String getTopicName() {
		return topicName;
	}
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
