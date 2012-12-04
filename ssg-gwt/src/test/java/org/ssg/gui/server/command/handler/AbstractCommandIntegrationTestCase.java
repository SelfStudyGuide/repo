package org.ssg.gui.server.command.handler;

import java.io.InputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;

/**
 * Provides helper methods for Command Handler integration testing.
 */
@ActiveProfiles(inheritProfiles = false, profiles = { "junit", "real-core", "junit-mock-security", "real-gui" })
public abstract class AbstractCommandIntegrationTestCase<R extends Response, A extends Action<R>> extends
        AbstractCommandTestCase<R, A> {

	@Autowired
	private IDatabaseTester databaseTester;

	protected void setUpData(String pathToDataset) throws Exception {
		InputStream stream = this.getClass().getResourceAsStream(pathToDataset);
		IDataSet dataSet = new FlatXmlDataSet(stream);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	protected void cleanUpData() throws Exception {
		databaseTester.onTearDown();
	}

}
