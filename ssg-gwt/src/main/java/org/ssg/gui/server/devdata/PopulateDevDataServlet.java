package org.ssg.gui.server.devdata;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class PopulateDevDataServlet extends HttpServlet {
	private static final long serialVersionUID = 6962590177702561482L;

	@Autowired
	private DefaultDevDataService devDataService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
		        .getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		devDataService.processRequest(req.getParameterMap(), resp.getWriter());

		resp.getWriter().flush();
	}

}
