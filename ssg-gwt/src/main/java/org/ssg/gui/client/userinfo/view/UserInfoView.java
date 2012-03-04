package org.ssg.gui.client.userinfo.view;

import org.ssg.gui.client.userinfo.presenter.UserInfoPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class UserInfoView implements Display {

	interface MyUiBinder extends UiBinder<HorizontalPanel, UserInfoView> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	public Anchor logoutBtn;

	@UiField
	public Label usernameLbl;

	public HasText getUsernameLabel() {
		return usernameLbl;
	}

	public HasClickHandlers getLogoutButton() {
		return logoutBtn;
	}

	public void go(RootPanel rootPanel) {
		rootPanel.add(uiBinder.createAndBindUi(this));
	}

}
