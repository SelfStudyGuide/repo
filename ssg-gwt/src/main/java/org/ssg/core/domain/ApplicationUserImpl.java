package org.ssg.core.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUserImpl implements UserDetails, ApplicationUser {
	private static final long serialVersionUID = 2299224597100254165L;
	private UserDetails underlying;
	private int personId;

	public ApplicationUserImpl(UserDetails underlying, int personId) {
		this.underlying = underlying;
		this.personId = personId;
	}
	
	public ApplicationUserImpl(UserDetails underlying) {
		this.underlying = underlying;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return underlying.getAuthorities();
	}

	public String getPassword() {
		return underlying.getPassword();
	}

	public String getUsername() {
		return underlying.getUsername();
	}

	public boolean isAccountNonExpired() {
		return underlying.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return underlying.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return underlying.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return underlying.isEnabled();
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
    	this.personId = personId;
    }

	public boolean containRole(UserRole roleName) {
		for (GrantedAuthority role : getAuthorities()) {
			if (role.getAuthority().equals(roleName.toString())) {
				return true;
			}
		}

		return false;
	}

}
