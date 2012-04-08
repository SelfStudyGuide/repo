package org.ssg.core.domain.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.ssg.core.dto.TopicProgressInfo;

public class TopicProgressInfoStatusTest {
	private TopicProgressInfo info;
	
	@Before
	public void setUp() {
		info = new TopicProgressInfo();
	}
	
	
	@Test
	public void verifyThatItIsDoneIdStatusIs100() {
		info.setStatus("100");
		
		assertThat(info.isDone(), is(true));
	}
	
	@Test
	public void verifyThatItIsInProgressIfLessThen100AnsGreatThen0() {
		info.setStatus("80");
		
		assertThat(info.isInProgress(), is(true));
	}
	
	@Test
	public void verifyThatItIsNotDoneIfLessThen100AnsGreatThen0() {
		info.setStatus("80");
		
		assertThat(info.isDone(), is(false));
	}
	
	@Test
	public void verifyThatItIsNotDoneIfStatusIs0() {
		info.setStatus("0");
		
		assertThat(info.isDone(), is(false));
	}
	
	@Test
	public void verifyThatItIsNotInProgressIfStatusIs100() {
		info.setStatus("100");
		
		assertThat(info.isInProgress(), is(false));
	}
	
	@Test
	public void verifyThatItIsNotInProgressIfStatusIs0() {
		info.setStatus("0");
		
		assertThat(info.isInProgress(), is(false));
	}

	@Test
	public void verifyThatItIsNotStartedIfStatusIs0() {
		info.setStatus("0");
		
		assertThat(info.isNotStarted(), is(true));
	}
	
	@Test
	public void verifyThatItIsStartedIfStatusIsGreatThen0() {
		info.setStatus("10");
		
		assertThat(info.isNotStarted(), is(false));
	}

}
