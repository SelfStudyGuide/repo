package org.ssg.gui.client.userinfo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.noMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.ssg.core.support.TstDataUtils;
import org.ssg.core.support.TstDataUtils.TestApplicationEvent;
import org.ssg.core.support.TstDataUtils.TestApplicationEventHandler;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;

import com.google.gwt.event.shared.HandlerManager;

public class FireEventWhenUserInfoTest {
	
	protected HandlerManager handlerManager;
	
	private FireEventWhenUserInfo tested;
	
	@Before
	public void setUp() {
		handlerManager = new HandlerManager(null);
		tested = new FireEventWhenUserInfo(handlerManager);
	}
	
	@Test
	public void givenAnActionWhenUserInfoIsReceivedThenTheActionShouldBeFired() {
		// Given
		TestApplicationEvent event = new TstDataUtils.TestApplicationEvent();
		tested.setEvent(event);
		TestApplicationEventHandler eventHandler = mock(TestApplicationEventHandler.class);
		handlerManager.addHandler(TestApplicationEvent.TYPE, eventHandler);
		
		// When
		handlerManager.fireEvent(new ShareUserInfoEvent(TstDataUtils.createStudentUserInfo("", 0)));
		
		// Then
		verify(eventHandler).process();
	}
	
	@Test
	public void givenNoActionSpecifiedUserInfoIsReceivedThenNoActionShouldBeFired() {
		// Given
		TestApplicationEventHandler eventHandler = mock(TestApplicationEventHandler.class);
		handlerManager.addHandler(TestApplicationEvent.TYPE, eventHandler);
		
		// When
		handlerManager.fireEvent(new ShareUserInfoEvent(TstDataUtils.createStudentUserInfo("", 0)));
		
		// Then
		verify(eventHandler, noMoreInteractions()).process();
	}
}
