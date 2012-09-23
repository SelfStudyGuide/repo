package org.ssg.test.functional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleFunctionalTest {

	private Tomcat mTomcat;

	private String mWorkingDir = System.getProperty("java.io.tmpdir") + "/" + getApplicationId();

	@Test
	public void test() throws LifecycleException {
		assertThat(mTomcat.getServer().getState(), is(LifecycleState.STARTED));
		
	}

	@Before
	public void before() throws LifecycleException {
		
		System.setProperty("spring.profiles.active", "functional");
		
	    mTomcat = new Tomcat();
		mTomcat.setPort(0);
		mTomcat.setBaseDir(mWorkingDir);
		mTomcat.getHost().setAppBase(mWorkingDir);
		mTomcat.getHost().setAutoDeploy(true);
		mTomcat.getHost().setDeployOnStartup(true);

		String contextPath = "/" + getApplicationId();
		File webApp = new File("target", "ssg-gui.war");

		mTomcat.addWebapp(mTomcat.getHost(), contextPath, webApp.getAbsolutePath());
		
		mTomcat.start();
    }

	@After
	public final void teardown() throws Throwable {
	  if (mTomcat.getServer() != null
	            && mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
	        if (mTomcat.getServer().getState() != LifecycleState.STOPPED) {
	              mTomcat.stop();
	        }
	        mTomcat.destroy();
	    }
	}
	
	private String getApplicationId() {
		return "ssg-gui-functional";
	}
}
