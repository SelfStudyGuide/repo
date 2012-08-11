package org.ssg.core.domain.adapter;

import org.springframework.beans.BeanUtils;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.dto.ApplicationUserInfo;

public class ApplicationUserAdpater {

	private final ApplicationUser user;

	public ApplicationUserAdpater(ApplicationUser user) {
		this.user = user;
	}

	public void populate(ApplicationUserInfo info) {
		BeanUtils.copyProperties(user, info);
		BeanUtils.copyProperties(this, info);
	}

	public int getStudentId() {
		if (user.containRole("student")) {
			return user.getPersonId();
		} else {
			return 0;
		}
	}

}
