package org.ssg.core.domain.adapter;

import org.springframework.beans.BeanUtils;
import org.ssg.core.domain.Homework;
import org.ssg.core.dto.HomeworkInfo;


public class HomeworkAdapter {
	private Homework homework;

	public HomeworkAdapter(Homework homework) {
		this.homework = homework;
	}

	public void populate(HomeworkInfo homeworkInfo) {
		BeanUtils.copyProperties(homework, homeworkInfo);
	}

}