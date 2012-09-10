package org.ssg.core.domain;

import java.io.Serializable;

/**
 * Contains answer to a question(exercise).
 * 
 * An answer is a part of a {@link Field}. Field can have and have not answer. The {@link Answer} domain object
 * should be have joins to homework, exercise, field etc as it may be stored in a separate db.
 * 
 * An answer should be requested from client side when exercise is displayed. (GetFieldAnswerAction)
 */
public class Answer implements Serializable {

	private static final long serialVersionUID = 2607058686947578677L;

	private String id;

	private int homeworkId;
	private int exerciseId;
	private String answer;
	private int fieldId;
	// Shows whether answer was correct or not or it does not matter
	// private AnswerStatus answerType
}
