package org.ssg.core.domain;

import java.util.List;

/**
 * Exercise has definition. Depends on the definition client can build 
 * ui element with given items.
 */
public class Exercise {
	private int id;
	private String name;
	private int order;
	private String description;
	private String definition;
	private List<Field> items;
}
