package org.ssg.core.domain;

import java.io.Serializable;

public interface ApplicationUser extends Serializable {

	String getUsername();

	int getPersonId();

	void setPersonId(int personId);

	boolean containRole(UserRole roleName);


}