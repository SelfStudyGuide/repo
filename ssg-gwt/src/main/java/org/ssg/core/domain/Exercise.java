package org.ssg.core.domain;

import java.util.List;

/**
 * Exercise has definition. Depends on the definition client can build ui
 * element with given items.
 * 
 * Each exercise definition should provider its own client DTO (QuestionsToText,
 * CompleteSentence). The DTO should define properties for all fields for given
 * exercise. The system should be able automatically adapt server to client
 * object. (GetExerciseAction) On GWT client side corresponded
 * <code>Presenter</code> and <code>Display</code> classes should be implemented.
 * 
 * Each exercise definition should register itself in the system, so that it can be available
 * for the editor.
 * 
 * When task page is opened on UI then it should request a configuration from server. The configuration 
 * should include mapping for exercise display.
 */
public class Exercise {
	private int id;
	private String name;
	private int order;
	private String description;
	private String definition;
	private List<Field> items;
}
