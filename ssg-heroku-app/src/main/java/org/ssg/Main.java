package org.ssg;

import org.apache.catalina.LifecycleException;

public class Main {
	public static void main(String[] args) throws LifecycleException {
		Starter starter = new Starter();
		starter.setWebApplicationPath("target/ssg-gui.war");
		starter.runAndWait();
	}
}
