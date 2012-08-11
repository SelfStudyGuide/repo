package org.ssg.core.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// TODO: This class should expose interface
public class ApplicationUser implements UserDetails {
	private static final long serialVersionUID = 2299224597100254165L;
	private UserDetails underlying;
	private int personId;

	public ApplicationUser(UserDetails underlying, int personId) {
		this.underlying = underlying;
		this.personId = personId;
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

	public boolean containRole(String roleName) {
		for (GrantedAuthority role : getAuthorities()) {
			if (role.getAuthority().equals(roleName)) {
				return true;
			}
		}

		return false;
	}

}
