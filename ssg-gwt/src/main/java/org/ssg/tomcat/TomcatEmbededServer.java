package org.ssg.tomcat;


public class TomcatEmbededServer {
	private String contextPath;
	private String webApplicationPath;
	private int port;
	

	
	public TomcatEmbededServer() {
		port = 8080;
		contextPath = "ssg-gui";
	}
	
	public void runAndWait() {
		
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
