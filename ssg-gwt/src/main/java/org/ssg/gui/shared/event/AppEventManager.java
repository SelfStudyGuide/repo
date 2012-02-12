package org.ssg.gui.shared.event;

import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppEventManager {
	private static final SimpleEventBus BUS = new SimpleEventBus();

	public static void subscibe(AppEventType<?> type, AppEventHandler handler) {
		BUS.addHandler(type, handler);
	}

	public static void publish(AppEvent ev) {
		BUS.fireEvent(ev);
	}
}
