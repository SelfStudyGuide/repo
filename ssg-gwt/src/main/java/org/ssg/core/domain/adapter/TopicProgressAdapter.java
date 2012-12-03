package org.ssg.core.domain.adapter;

import org.springframework.beans.BeanUtils;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.TopicProgressInfo;

public class TopicProgressAdapter {

	private final TopicProgress topicProgress;

	public TopicProgressAdapter(TopicProgress topicProgress) {
		this.topicProgress = topicProgress;
	}

	public void populate(TopicProgressInfo info) {
		BeanUtils.copyProperties(topicProgress, info);
		BeanUtils.copyProperties(this, info);
	}

}
