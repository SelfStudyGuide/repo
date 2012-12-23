package org.ssg.gui.client.patch;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.gwt.http.client.URL;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(URL.class)
public class URLOverridenPatcher {

	@PatchMethod()
	static String decodeQueryStringImpl(String encodedURLComponent) {
		try {
			return URLDecoder.decode(encodedURLComponent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new GwtTestPatchException(e);
		}
	}
}
