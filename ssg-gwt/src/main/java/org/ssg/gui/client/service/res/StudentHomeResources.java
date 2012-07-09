package org.ssg.gui.client.service.res;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface StudentHomeResources extends ClientBundle {
	public static final StudentHomeResources INSTANCE = GWT
			.create(StudentHomeResources.class);

	@Source("/StudentHome.css")
	@CssResource.NotStrict
	CssResource styles();
}
