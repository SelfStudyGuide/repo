package org.ssg.gui.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.UserRole;
import org.ssg.core.service.UserDao;
import org.ssg.gui.server.ApplicationMessageSource;

@Service
public class DefaultSecurityService implements SecurityService {

	@Autowired
	private UserDao userDao;

	private MessageSourceAccessor applicationMsg = ApplicationMessageSource.getAccessor();

	private int retriveStudentIdByName(String userName) {

		Student student = userDao.getStudentByName(userName);

		if (student == null) {
			throw new SsgServiceException(applicationMsg.getMessage("ssg.auth.studentNotFoundByName",
			        new String[] { userName }));
		}

		return student.getId();
	}

	public void enrichWithPersonId(ApplicationUser user) {
		int personId = 0;
		if (user.containRole(UserRole.student)) {
			personId = retriveStudentIdByName(user.getUsername());
		} else if (user.containRole(UserRole.teacher)) {
			// TODO: derive teacher id;
		} else {
			throw new SsgServiceException(applicationMsg.getMessage("ssg.auth.userDoesNotHaveRole",
			        new String[] { user.getUsername() }));
		}

		user.setPersonId(personId);
	}

}
