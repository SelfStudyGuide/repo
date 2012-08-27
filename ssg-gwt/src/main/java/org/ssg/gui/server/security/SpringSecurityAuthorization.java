package org.ssg.gui.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.ssg.core.domain.Homework;
import org.ssg.core.service.HomeworkDao;
import org.ssg.gui.server.ApplicationMessageSource;

/**
 * SpringSecurity implementation of {@link Authorization}. It redirect all
 * methods calls to target annotated implementation of {@link Authorization}.
 * This class handle all {@link AccessDeniedException} exceptions and re-throw
 * {@link SsgSecurityException}.
 * 
 */
@Service(value = "authorization")
public class SpringSecurityAuthorization implements Authorization {

	@Autowired(required = true)
	@Qualifier("targetSpringSecurityAuthorisation")
	private TargetSpringSecurityAuthorisation target;

	@Autowired(required = true)
	private HomeworkDao homeworkDao;

	private MessageSourceAccessor messages = ApplicationMessageSource.getAccessor();

	public void ownStudent(int studentId) {
		target.ownStudent(studentId);
	}

	public void student() {
		try {
			target.student();
		} catch (AccessDeniedException e) {
			throw new SsgSecurityException(messages.getMessage("ssg.authorize.stundet"), e);
		}
	}

	public void ownHomework(int hwId) throws SsgSecurityException {
		try {
			Homework homework = homeworkDao.getHomework(hwId);
			target.ownHomework(homework);
		} catch (AccessDeniedException e) {
			throw new SsgSecurityException(messages.getMessage("ssg.authorize.ownhomework"), e);
		}
	}

}
