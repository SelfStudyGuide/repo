package org.ssg.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HOMEWORK")
public class Homework {
	
	private int id;
	private int moduleId;
	private Student student;
	private List<TopicProgress> progresses;

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "MODULE_ID", nullable = false)
	public int getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public void setModule(Module module) {
		if (module != null) {
			this.moduleId = module.getId();
		}
	}

	@ManyToOne
	@JoinColumn(name = "STUD_ID")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homework", orphanRemoval = true)
	public List<TopicProgress> getProgresses() {
		return progresses;
	}

	public void setProgresses(List<TopicProgress> progresses) {
		this.progresses = progresses;
	}

	public void initTopicProgress(List<Topic> topics) {
		ArrayList<TopicProgress> list = new ArrayList<TopicProgress>(topics.size());
		for (Topic topic : topics) {
			list.add(new TopicProgress(topic, this));
		}
		setProgresses(list);
	}

	/**
	 * Lookups TopicProgress by given topidId. Returns null if nothing found.
	 */
	public TopicProgress getTopicProgress(int topicId) {
		TopicProgress result = null;

		if (getProgresses() != null && !getProgresses().isEmpty()) {

			for (TopicProgress t : getProgresses()) {
				if (t.getTopicId() == topicId) {
					result = t;
					break;
				}
			}

		}
		return result;
	}

	public boolean hasTopic(int topicId) {
		return getTopicProgress(topicId) != null;
	}
	
	public boolean hasExercise(Exercise exercise) {
	    Task task = exercise.getTask();
        if (task == null) {
            throw new IllegalStateException(String.format("Exercise %s does not has task", exercise.getId()));
        }
        Topic topic = task.getTopic();
        if (topic == null) {
            throw new IllegalStateException(String.format("Task %s does not has topic", task.getId()));
        }
        return hasTopic(topic.getId());
	}

}
