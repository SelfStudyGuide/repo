package org.ssg.gui.client.service.res;

public class SsgLookupMessagesHelper {

	public static String dot2underscore(String key) {
		return key != null ? key.replace('.', '_') : null;
	}
}
