package org.ssg.gui.client.userinfo.view;

import org.ssg.gui.client.userinfo.presenter.UserInfoPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserInfoView implements Display {

	interface UserInfoViewUiBinder extends UiBinder<Widget, UserInfoView> {
	}

	private static UserInfoViewUiBinder uiBinder = GWT.create(UserInfoViewUiBinder.class);

	@UiField()
	public Anchor logoutBtn;

	// public DivElement usernameLbl;

	@UiField()
	public HasText usernameLbl;

	public HasText getUsernameLabel() {
		return usernameLbl;
	}

	public HasClickHandlers getLogoutButton() {
		return logoutBtn;
	}

	public void go(RootPanel rootPanel) {
		Widget a = uiBinder.createAndBindUi(this);
		rootPanel.add(a);
	}

}
