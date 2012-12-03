package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.ssg.gui.client.topic.action.GetTopicTasksInfo;
import org.ssg.gui.client.topic.action.GetTopicTasksInfoResponse;

@ActiveProfiles(inheritProfiles = false, profiles = { "junit", "real-core", "junit-mock-security", "real-gui" })
public class GetTopicTasksInfoActionHandlerIntegrationTest extends
        AbstractCommandTestCase<GetTopicTasksInfoResponse, GetTopicTasksInfo> {

    @Autowired
    private IDatabaseTester databaseTester;

    @Before
    public void setUp() throws Exception {
        InputStream stream = this.getClass().getResourceAsStream(
                "/dbunit/GetTopicTasksInfoActionHandlerIntegrationTest.xml");
        IDataSet dataSet = new FlatXmlDataSet(stream);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    @Test
    public void verifyThatTaskInfosAreLoaded() {
        GetTopicTasksInfoResponse response = whenAction(new GetTopicTasksInfo(10, 40));

        assertThat(response, notNullValue());
        assertThat(response.getTaskInfos().size(), CoreMatchers.is(3));
    }

    @Override
    protected Class<GetTopicTasksInfo> testedCommandClass() {
        return GetTopicTasksInfo.class;
    }

}
