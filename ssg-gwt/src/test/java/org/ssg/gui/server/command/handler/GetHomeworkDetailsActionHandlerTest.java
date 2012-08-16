package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.StudentService;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;

public class GetHomeworkDetailsActionHandlerTest extends
        AbstractCommandTestCase<GetHomeworkDetailsResponse, GetHomeworkDetails> {

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
