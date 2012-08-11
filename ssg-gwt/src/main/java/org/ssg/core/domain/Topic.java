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
@Table(name = "TOPIC")
public class Topic {

	private int id;
	private String name;
	private String description;
	private Module module;

	private List<Task> tasks;

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne()
	// @JoinColumn(name="MOD_ID", nullable=false)
	@JoinColumn(name = "MOD_ID")
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", orphanRemoval = true)
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public static void assignTask(Topic topic, Task task) {
		List<Task> tasks = topic.getTasks();
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
		tasks.add(task);
		topic.setTasks(tasks);
		task.setTopic(topic);
	}

}
