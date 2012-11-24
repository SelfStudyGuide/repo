package org.ssg.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ssg.core.dto.ExerciseType;

/**
 * Exercise has definition. Depends on the definition client can build ui
 * element with given items.
 * 
 * Each exercise definition should provider its own client DTO (QuestionsToText,
 * CompleteSentence). The DTO should define properties for all fields for given
 * exercise. The system should be able automatically adapt server to client
 * object. (GetExerciseAction) On GWT client side corresponded
 * <code>Presenter</code> and <code>Display</code> classes should be
 * implemented.
 * 
 * Each exercise definition should register itself in the system, so that it can
 * be available for the editor.
 * 
 * When task page is opened on UI then it should request a configuration from
 * server. The configuration should include mapping for exercise display.
 */
@Entity
@Table(name = "EXERCISE")
public class Exercise {
	private int id;
	private String name;
	private int order;
	private String description;
	private ExerciseType exerciseType;
	private List<Field> fields;
	private Task task;

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

	@Column(name = "EXERCISE_ORDER", nullable = false)
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	public ExerciseType getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(ExerciseType exerciseType) {
		this.exerciseType = exerciseType;
	}

	@Transient
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_ID")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
