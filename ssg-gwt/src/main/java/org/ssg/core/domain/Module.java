package org.ssg.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MODULE")
public class Module {
	private int id;
	private String name;
	private String description;
	private List<Topic> topics;
	//private List<Homework> homeworks;

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESC")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "module", orphanRemoval = true)
	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

//	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
//			mappedBy = "modules", targetEntity = Homework.class)
//	public List<Homework> getHomeworks() {
//		return homeworks;
//	}
//
//	public void setHomeworks(List<Homework> homeworks) {
//		this.homeworks = homeworks;
//	}

}
