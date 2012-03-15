package org.ssg.gui.client.studenthome.presenter;

import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.ActionSender;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEvent;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEventHandler;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.view.client.HasData;

public class HomeworkPresenter {
	private HandlerManager handlerManager;
	private Display view;
	private ApplicationUserInfo userInfo;
	private ActionSender actionSender;
	private ErrorDialog errorDialog;

	public interface Display {
		HasData<HomeworkInfo> getGrid();

		HasClickHandlers getRefreshButton();

		HasText getDebugMessage();
	}

	public HomeworkPresenter(Display view, ActionSender actionSender,
			HandlerManager handlerManager, ErrorDialog errorDialog) {
		this.view = view;
		this.actionSender = actionSender;
		this.handlerManager = handlerManager;
		this.errorDialog = errorDialog;
	}

	public void bind() {
		handlerManager.addHandler(RefreshStudentHomeworksEvent.TYPE,
				new RefreshStudentHomeworksEventHandler() {
					public void onHomeworkRefresh() {
						doHomeworksRequest();
					}
				});

		view.getRefreshButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handlerManager.fireEvent(new RefreshStudentHomeworksEvent());
			}
		});

		handlerManager.addHandler(ShareUserInfoEvent.TYPE,
				new ShareUserInfoEventHandler() {
					public void onUserInfoRetrieve(ShareUserInfoEvent event) {
						userInfo = event.getUserInfo();
						handlerManager
								.fireEvent(new RefreshStudentHomeworksEvent());
					}
				});
	}

	protected void doHomeworksRequest() {
		if (userInfo == null) {
			// TODO: display error
		} else {
			actionSender.send(new GetHomeworks(userInfo.getStudentId()),
					new ActionCallbackAdapter<GetHomeworksResponse>() {

						@Override
						public void onResponse(GetHomeworksResponse result) {
							view.getGrid().setRowCount(
									result.getHomeworks().size());
							view.getGrid().setRowData(0, result.getHomeworks());
							
							//errorDialog.show("Received " + result.getHomeworks().size() + " items", this.getAction());
						}
					});

		}
	}

	public ApplicationUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ApplicationUserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
