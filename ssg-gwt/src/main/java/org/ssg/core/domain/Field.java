package org.ssg.core.domain;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ssg.core.dto.FieldType;

// This is abstract class. It can be extended with MultilineTextField, Table 
// with additional properties
//@Entity
//@Table(name="FIELD")
//@Inheritance(strategy=SINGLE_TABLE)
//@DiscriminatorColumn(name="FIELD_TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
public abstract class Field {

	/** Defines id of a field in a task */
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	private int fieldId;

	/** Type of fields (eg. SingleLineText, MultiLineText etc) */
	@Enumerated(EnumType.STRING)
	@Column(name = "FIELD_TYPE", nullable = false)
	private FieldType type;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "VALUE")
	private String value;

	@Transient
	private List<Field> childFields;

	// Fields should implements WithAnswer interface
}
