package org.ssg.gui.client.patch;

import org.ssg.gui.client.UrlQueryString;

import com.google.gwt.user.client.impl.WindowImpl;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(WindowImpl.class)
public class WindowImplOverridenPatcher {

	@PatchMethod(override = true)
	static String getQueryString(WindowImpl windowImpl) {
		return UrlQueryString.getUrlQueryString();
	}

}
