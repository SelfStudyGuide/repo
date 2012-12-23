package org.ssg.gui.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class UrlQueryString {
	private static Map<String, String> params = new HashMap<String, String>();

	public static void add(String key, String val) {
		params.put(key, val);
	}

	public static void clear() {
		params.clear();
	}

	public static String getUrlQueryString() {
		StringBuilder result = new StringBuilder(startWithQuestionMarkIfAnyParams());

		for (Iterator<Entry<String, String>> i = params.entrySet().iterator(); i.hasNext();) {
			Entry<String, String> param = i.next();
			result.append(param.getKey()).append("=").append(param.getValue());
			result.append(i.hasNext() ? "&" : "");
		}
		return result.toString();
	}

	private static String startWithQuestionMarkIfAnyParams() {
		return params.size() > 0 ? "?" : "";
	}
}
