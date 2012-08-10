package org.ssg.gui.client.service.res;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface StudentHomeResources extends ClientBundle {
	public static final StudentHomeResources INSTANCE = GWT
			.create(StudentHomeResources.class);

	@Source("/StudentHome.css")
	@CssResource.NotStrict
	Style styles();

	public interface Style extends CssResource {
		String floatLeft();

		String floatRight();
		
		String clear();
		
		String widget_title();
		
		String clickButton();
		
		String paddingRight5();
		
		String paddingRight10();
	}
}
