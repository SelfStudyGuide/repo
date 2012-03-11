package org.ssg.gui.client.service;

import org.ssg.core.dto.ApplicationUserInfo;

/**
 * Class which provide static method to get access to shared
 * {@link ApplicationUserInfo}
 */
public class UserInfoHolder {

	private static ApplicationUserInfo info;

	public static void setAppInfo(ApplicationUserInfo userInfo) {
		info = userInfo;
	}

	public static String getAppUsername() {
		if (info != null) {
			return info.getUsername();
		}
		return null;
	}

	public static int getStudentId() {
		if (info != null) {
			return info.getStudentId();
		}
		return -1;
	}
}
