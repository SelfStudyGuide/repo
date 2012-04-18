package org.ssg.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "HOMEWORK")
public class Homework {
	private int id;
	private List<Module> modules;
	private Student student;
	private List<TopicProgress> progresses;

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToMany(cascade={CascadeType.ALL},
			targetEntity=Module.class)
	@JoinTable(name = "HOMEWORK_MODULE", 
			joinColumns = @JoinColumn(name = "HW_ID"), 
			inverseJoinColumns = @JoinColumn(name = "MODULE_ID"))
	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	@ManyToOne
	@JoinColumn(name = "STUD_ID")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "homework", orphanRemoval = true)
	public List<TopicProgress> getProgresses() {
		return progresses;
	}

	public void setProgresses(List<TopicProgress> progresses) {
		this.progresses = progresses;
	}
	
}
