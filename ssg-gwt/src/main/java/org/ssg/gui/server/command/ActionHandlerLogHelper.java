package org.ssg.gui.server.command;

import org.apache.commons.logging.Log;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.BaseAction;

public class ActionHandlerLogHelper {

	public static void info(Log log, Action<?> act, String message, Object... args) {
		if (log.isInfoEnabled()) {
			if (act instanceof BaseAction<?>) {
				String actName = ((BaseAction<?>) act).getActionName();
				log.info(actName + " : " + String.format(message, args));
			} else {
				log.info(String.format(message, args));
			}
		}
	}

	public static void debug(Log log, Action<?> act, String message, Object... args) {
		if (log.isDebugEnabled()) {
			if (act instanceof BaseAction<?>) {
				String actName = ((BaseAction<?>) act).getActionName();
				log.debug(actName + " : " + String.format(message, args));
			} else {
				log.debug(String.format(message, args));
			}
		}
	}
}
