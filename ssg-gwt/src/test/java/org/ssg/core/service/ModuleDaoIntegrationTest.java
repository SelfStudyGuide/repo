package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class ModuleDaoIntegrationTest extends AbstractDaoTestSupport {

	@Override
	protected void cleanUpDb() {
		super.cleanUpDb();
		deleteAll(Homework.class);
		deleteAll(Module.class);
	}

	@Test
	// @Rollback(value=false)
	public void givenPercistedModuleInDbWhenGetAllModulesThenItShouldBeLoaded() {
		Module module = TstDataUtils.createModule();
		curriculumDao.saveModule(module);

		Collection<Module> modules = curriculumDao.getAllModules();
		Assert.assertThat(modules.size(), is(1));
		Module savedModule = modules.iterator().next();
		Assert.assertThat(savedModule.getName(), equalTo("name"));
		Assert.assertThat(savedModule.getDescription(), equalTo("desc"));
		Assert.assertThat(savedModule.getId(), is(not(0)));

	}

	// TODO: It should be re-done with implicit duplication check, and throw
	// application exception
	@Test(expected = DataIntegrityViolationException.class)
	public void verifyThatExceptionIsThownWhenTwoModulesWithTheSameNameHasBeenCommitted() {
		Module module = TstDataUtils.createModule();
		curriculumDao.saveModule(module);

		Module module2 = TstDataUtils.createModule();
		curriculumDao.saveModule(module2);

		template.flush();
	}

}
