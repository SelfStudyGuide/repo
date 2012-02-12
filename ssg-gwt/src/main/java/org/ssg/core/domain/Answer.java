package org.ssg.core.domain;

import java.io.Serializable;


public class Answer implements Serializable {
	private static final long serialVersionUID = 2607058686947578677L;
	//private int userId;
	private Homework homework;
	private String fieldDefId;
	private String id;
	private String answer;
	private Field field;
}
