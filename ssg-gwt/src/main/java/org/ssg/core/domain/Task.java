package org.ssg.core.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.ssg.core.dto.TaskType;

/**
 * Task aggregates exercises according to type of task (text, listening...)
 * Task should have execrisesCount fields which then will used to 
 * calculate completed task
 */
@Entity
@Table(name = "TOPIC_TASK")
public class Task {
	private int id;
	private String name;
	private List<Exercise> exercises;
	private TaskType type;
	private String description;
	private Topic topic;
	private int execrisesCount;
	
	@Id
    @GeneratedValue
    @Column(name = "ID", nullable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "NAME", nullable=false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable=false)
	public TaskType getType() {
		return type;
	}
	
	public void setType(TaskType type) {
		this.type = type;
	}

	
	@ManyToOne()
	@JoinColumn(name="TOPIC_ID")
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "EXECRCISE_CNT")
	public int getExecrisesCount() {
		return execrisesCount;
	}

	
	public void setExecrisesCount(int execrisesCount) {
		this.execrisesCount = execrisesCount;
	}
	
	
	
}
