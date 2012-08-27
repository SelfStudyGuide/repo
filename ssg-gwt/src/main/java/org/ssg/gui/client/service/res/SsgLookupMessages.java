package org.ssg.gui.client.service.res;

/**
 * Interface to represent the constants contained in resource bundle:
 * 'E:/Work/Projects/SelfStudyGuide/svn/trunk/ssg-gwt/src/main/resources/org/ssg/gui/client/service/res/SsgLookupMessages.properties'
 * .
 */
public interface SsgLookupMessages extends com.google.gwt.i18n.client.ConstantsWithLookup {

	/**
	 * Translated "System cannot find topic details".
	 * 
	 * @return translated "System cannot find topic details"
	 */
	@DefaultStringValue("System cannot find topic details")
	@Key("topic.view.notfound")
	String topic_view_notfound();
	
	@Key("security.accessdeny")
	String security_accessdeny();
}
