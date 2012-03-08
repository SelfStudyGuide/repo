package org.ssg.gui.client.studenthome.presenter;

import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEvent;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEventHandler;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.view.client.HasData;

public class HomeworkPresenter {
	private HandlerManager handlerManager;
	private Display view;
	private StudentControlServiceAsync service;
	private ApplicationUserInfo userInfo;

	public interface Display {
		HasData<HomeworkInfo> getGrid();

		HasClickHandlers getRefreshButton();

		HasText getDebugMessage();
	}

	public HomeworkPresenter(Display view, StudentControlServiceAsync service,
			HandlerManager handlerManager) {
		this.view = view;
		this.service = service;
		this.handlerManager = handlerManager;
		
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
			service.execute(new GetHomeworks(userInfo.getStudentId()),
					new GetHomeworksResponseCallback());

		}
	}
	
	public ApplicationUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ApplicationUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	private class GetHomeworksResponseCallback implements
			AsyncCallback<GetHomeworksResponse> {

		public void onFailure(Throwable caught) {
			GWT.log("Cannot get list of homeworks", caught);
		}

		public void onSuccess(GetHomeworksResponse result) {
			view.getGrid().setRowCount(result.getHomeworks().size());
			view.getGrid().setRowData(0, result.getHomeworks());

			// int id = result.getHomeworks().iterator().next().getId();
			// view.getDebugMessage().setText(String.valueOf(id));
		}
	}

}
