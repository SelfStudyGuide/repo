package org.ssg.gui.shared.event;


public class ApplicationEvents {
	public static AppEventType<AppEvent> REFRESH_STUD_TASKS = new AppEventType<AppEvent>() {
		@Override
		public AppEvent getEvent() {
			return new AppEvent(REFRESH_STUD_TASKS);
		}
	};
}
