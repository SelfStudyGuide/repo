package org.ssg.core.domain;

import java.util.List;

// This is abstract class. It can be extended with MultilineTextField, Table 
// with additional properties
public class Field {
	/** Defines name of a field in a task */
	private String definitionId;
	/** Type of fields (eg. SingleLineText, MultiLineText etc) */
	private String type;
	private String title;
	private String value;
	private List<Field> childFields;
}
