package org.ssg.core.support;

import org.hamcrest.CoreMatchers;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"junit", "real-core", "junit-mock-security"})
@ContextConfiguration(locations = { "classpath:/spring/test-application.ctx.xml" })
public class SchemaExportTool {

	@Autowired
	@Qualifier("sessionFactory")
	private LocalSessionFactoryBean factoryBean;

	@Test	
	public void execute() {
		System.setProperty("hibernate.hbm2ddl", "create");
		Assert.assertThat(factoryBean, CoreMatchers.notNullValue());
		Configuration configuration = factoryBean.getConfiguration();
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.setOutputFile("schema_output.sql");
		schemaExport.create(true, false);
	}
}
