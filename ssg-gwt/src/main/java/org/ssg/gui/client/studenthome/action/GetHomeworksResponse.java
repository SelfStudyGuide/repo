package org.ssg.gui.client.studenthome.action;

import java.io.Serializable;
import java.util.ArrayList;

import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.shared.Response;

public class GetHomeworksResponse implements Response, Serializable {
	private static final long serialVersionUID = -8278997593973008184L;

	private ArrayList<HomeworkInfo> homeworks;

	public GetHomeworksResponse() {
		
	}
	
	public GetHomeworksResponse(ArrayList<HomeworkInfo> homeworks) {
		this.homeworks = homeworks;
	}

	public ArrayList<HomeworkInfo> getHomeworks() {
		return homeworks;
	}

}
