package org.ssg.gui.server.command.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.adapter.ApplicationUserAdpater;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.userinfo.action.GetUserInfo;
import org.ssg.gui.client.userinfo.action.GetUserInfoResponse;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.service.SecurityService;

@Service
public class GetUserInfoActionHandler implements ActionHandler<GetUserInfoResponse, GetUserInfo> {

	private static final Log LOG = LogFactory.getLog(GetUserInfoActionHandler.class);

	@Autowired
	private SecurityService securityService;

	public GetUserInfoResponse execute(GetUserInfo action) throws SsgGuiServiceException {
		ApplicationUserInfo into = new ApplicationUserInfo();

		ApplicationUser user = securityService.getAuthorisedUserInfo();

		if (user == null) {
			throw new SsgGuiServiceException("User id not authenticated", "userNotLoggedIn");
		}

		ApplicationUserAdpater adapter = new ApplicationUserAdpater(user);
		adapter.populate(into);

		return new GetUserInfoResponse(into);
	}

	public Class<GetUserInfo> forClass() {
		return GetUserInfo.class;
	}

}
