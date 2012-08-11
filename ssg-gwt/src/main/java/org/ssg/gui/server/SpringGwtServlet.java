package org.ssg.gui.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SpringGwtServlet extends RemoteServiceServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
		        .getAutowireCapableBeanFactory().autowireBean(this);
	}
}
