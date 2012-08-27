package org.ssg.gui.server.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.ssg.core.domain.Homework;


@Service(value = "targetSpringSecurityAuthorisation")
public class TargetSpringSecurityAuthorisation  {

	@PreAuthorize("#studentId == authentication.principal.personId")
	public void ownStudent(int studentId) {

	}

	@PreAuthorize("hasRole('student')")
	public void student() {
	}

	@PreAuthorize("#hw.student.id == authentication.principal.personId")
	public void ownHomework(Homework hw) throws SsgSecurityException {
    }

}
