package org.ssg.gui.client.userinfo.action;

import java.io.Serializable;

import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.gui.shared.Response;

public class GetUserInfoResponse implements Response, Serializable {
	private static final long serialVersionUID = 6275941025176248880L;

	private ApplicationUserInfo userInfo;

	public GetUserInfoResponse() {
	}

	public GetUserInfoResponse(ApplicationUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public ApplicationUserInfo getUserInfo() {
		return userInfo;
	}
}
