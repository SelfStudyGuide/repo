package org.ssg.core.domain.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.dto.TopicProgressInfo;

public class HomeworkAdapter {
	private static final ArrayList<TopicProgressInfo> EMPTY = new ArrayList<TopicProgressInfo>();

	private final Homework homework;
	private final boolean moduleDetails;

	public HomeworkAdapter(Homework homework, boolean moduleDetails) {
		this.homework = homework;
		this.moduleDetails = moduleDetails;
	}

	public void populate(HomeworkInfo homeworkInfo) {
		BeanUtils.copyProperties(homework, homeworkInfo);
		BeanUtils.copyProperties(this, homeworkInfo);
	}

	public String getAssignedModules() {
		List<Module> modules = homework.getModules();

		if (modules == null) {
			return "";
		}

		StringBuffer assignedMod = new StringBuffer();
		for (Module module : modules) {
			if (assignedMod.length() > 0) {
				assignedMod.append(", ");
			}
			assignedMod.append(module.getName());
		}
		return assignedMod.toString();
	}

	public ArrayList<TopicProgressInfo> getTopicProgress() {

		List<TopicProgress> progresses = homework.getProgresses();

		if (progresses == null || !moduleDetails) {
			return EMPTY;
		}

		return iterateTopicProgressAndAdaptItToInfo(progresses);
	}

	private ArrayList<TopicProgressInfo> iterateTopicProgressAndAdaptItToInfo(
			List<TopicProgress> progresses) {
		ArrayList<TopicProgressInfo> result = new ArrayList<TopicProgressInfo>();
		for (TopicProgress tp : progresses) {
			
			TopicProgressInfo info = new TopicProgressInfo();
			new TopicProgressAdapter(tp).populate(info);
			result.add(info);
		}
		return result;
	}
	
}