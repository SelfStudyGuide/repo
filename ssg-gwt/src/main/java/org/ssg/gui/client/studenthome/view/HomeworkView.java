package org.ssg.gui.client.studenthome.view;

import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.studenthome.StudentHomeworkMessages;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter.Display;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.HasData;

public class HomeworkView implements Display {

	interface MyUiBinder extends UiBinder<VerticalPanel, HomeworkView> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	// @UiField
	public Label debugMsg;
	@UiField
	public Button refreshButton;

	@UiField
	public CellTable<HomeworkInfo> homeworksTable;

	private final StudentHomeworkMessages messages;

	public HomeworkView(StudentHomeworkMessages homeworkMessages) {
		this.messages = homeworkMessages;
	}

	public HasData<HomeworkInfo> getGrid() {
		return homeworksTable;
	}

	public HasClickHandlers getRefreshButton() {
		return refreshButton;
	}

	public void go(RootPanel rootPanel) {
		rootPanel.add(uiBinder.createAndBindUi(this));

		initGridColumns();

	}

	private void initGridColumns() {

		Column<HomeworkInfo, String> idColumn = new Column<HomeworkInfo, String>(
				new TextCell()) {
			@Override
			public String getValue(HomeworkInfo object) {
				return String.valueOf(object.getId());
			}
		};
		
		Column<HomeworkInfo, String> modulesColumn = new Column<HomeworkInfo, String>(
				new TextCell()) {
			@Override
			public String getValue(HomeworkInfo object) {
				return "Module 2";
			}
		};
		
		Column<HomeworkInfo, String> statusColumn = new Column<HomeworkInfo, String>(
				new TextCell()) {
			@Override
			public String getValue(HomeworkInfo object) {
				return "Not started";
			}
		};

		homeworksTable.addColumn(idColumn, messages.homeworkTableId());
		homeworksTable.addColumn(modulesColumn, messages.homeworkTableModule());
		homeworksTable.addColumn(statusColumn, messages.homeworkTableStatus());
	}

	public HasText getDebugMessage() {
		return debugMsg;
	}

}
