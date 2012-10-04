package org.ssg;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Starter {
	private String contextPath;
	private String webApplicationPath;
	private int port;

	private Tomcat mTomcat;

	public Starter() {
		port = 8080;
		contextPath = "/ssg-gui";
	}

	public void runAndWait() throws LifecycleException {
		String mWorkingDir = System.getProperty("java.io.tmpdir") + contextPath;
		mTomcat = new Tomcat();
		mTomcat.setPort(port);
		mTomcat.setBaseDir(mWorkingDir);
		mTomcat.getHost().setAppBase(mWorkingDir);
		mTomcat.getHost().setAutoDeploy(true);
		mTomcat.getHost().setDeployOnStartup(true);

		File webApp = new File(webApplicationPath);

		mTomcat.addWebapp(mTomcat.getHost(), contextPath, webApp.getAbsolutePath());

		mTomcat.start();
		mTomcat.getServer().await();
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getWebApplicationPath() {
		return webApplicationPath;
	}

	public void setWebApplicationPath(String webApplicationPath) {
		this.webApplicationPath = webApplicationPath;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
