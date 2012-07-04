package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.StudentService;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;


@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml",
"/serice-core-mock.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GetHomeworkDetailsActionHandlerTest extends AbstractCommandTestCase<GetHomeworkDetailsResponse, GetHomeworkDetails>{

	@Autowired
	private StudentService service;
	
	@Override
	protected Class<GetHomeworkDetails> testedCommandClass() {
		return GetHomeworkDetails.class;
	}
	
	@Test
	public void verifyThatHomeworkDetailsIsReturnedInAResultOfAction() {
		HomeworkInfo homeworkInfoFromService = TstDataUtils.createHomeworkInfoWithDetails();
		when(service.getHomeworksDetails(eq(456))).thenReturn(homeworkInfoFromService);

		GetHomeworkDetails getHomeworkDetails = new GetHomeworkDetails(123, 456);
		HomeworkInfo homeworkInfo = whenAction(getHomeworkDetails).getHomework();
		
		assertThat(homeworkInfo, is(homeworkInfoFromService));
	}

}
