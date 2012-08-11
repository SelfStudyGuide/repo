package org.ssg.core.domain;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = 2607058686947578677L;

	private Homework homework;
	private Exercise exercise;
	private String id;
	private String answer;
	private Field field;
	// Shows whether answer was correct or not or it does not matter
	// private AnswerStatus answerType
}
