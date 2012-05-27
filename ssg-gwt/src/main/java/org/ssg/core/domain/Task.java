package org.ssg.core.domain;

import java.util.List;

public class Task {
	private int id;
	private String name;
	private List<Exercise> exercises;
	private TaskType type;
	/** Fields contain description of a task */
	private List<Field> items;
}
