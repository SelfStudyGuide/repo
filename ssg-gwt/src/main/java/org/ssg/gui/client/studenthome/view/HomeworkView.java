package org.ssg.gui.client.studenthome.view;

import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter.Display;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SingleSelectionModel;

public class HomeworkView implements Display {

	interface MyUiBinder extends UiBinder<Widget, HomeworkView> {
	}

	interface HomeworkTableResources extends CellTable.Resources {
		@Source({ CellTable.Style.DEFAULT_CSS, "HomeworkTable.css" })
		HomeworkTableStyle cellTableStyle();
	}

	interface HomeworkTableStyle extends CellTable.Style {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private static HomeworkTableResources homeworkTableResources = GWT.create(HomeworkTableResources.class);

	// @UiField
	public Label debugMsg;
	@UiField
	public Button refreshButton;

	@UiField
	public CellTable<HomeworkInfo> homeworksTable;

	private final SsgMessages messages;

	private SingleSelectionModel<HomeworkInfo> selectionModel;

	public HomeworkView(SsgMessages homeworkMessages) {
		this.messages = homeworkMessages;
	}

	public HasData<HomeworkInfo> getGrid() {
		return homeworksTable;
	}

	public HasClickHandlers getRefreshButton() {
		return refreshButton;
	}

	public SingleSelectionModel<HomeworkInfo> getSelectionModel() {
		return selectionModel;
	}

	public void go(RootPanel rootPanel) {
		rootPanel.add(uiBinder.createAndBindUi(this));

		initGridColumns();
	}

	@UiFactory
	CellTable<HomeworkInfo> makeHomeworkTable() {
		return new CellTable<HomeworkInfo>(10, homeworkTableResources);
	}

	private void initGridColumns() {
		Column<HomeworkInfo, String> idColumn = new Column<HomeworkInfo, String>(new TextCell()) {
			@Override
			public String getValue(HomeworkInfo object) {
				return String.valueOf(object.getId());
			}
		};

		Column<HomeworkInfo, String> modulesColumn = new Column<HomeworkInfo, String>(new TextCell()) {
			@Override
			public String getValue(HomeworkInfo object) {
				return object.getAssignedModules();
			}
		};

		Column<HomeworkInfo, String> statusColumn = new Column<HomeworkInfo, String>(new TextCell()) {
			@Override
			public String getValue(HomeworkInfo object) {
				return "Not started";
			}
		};

		homeworksTable.addColumn(idColumn, messages.homeworkTableId());
		homeworksTable.addColumn(modulesColumn, messages.homeworkTableModule());
		homeworksTable.addColumn(statusColumn, messages.homeworkTableStatus());

		selectionModel = new SingleSelectionModel<HomeworkInfo>();
		homeworksTable.setSelectionModel(selectionModel);

	}

	public HasText getDebugMessage() {
		return debugMsg;
	}

}
