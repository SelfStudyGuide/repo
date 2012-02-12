package org.ssg.core.domain;

import java.util.List;

public class Task {
	private int id;
	private String name;
	private List<Exercise> exercises;
	private TaskType type;
	private List<Field> items;
}
