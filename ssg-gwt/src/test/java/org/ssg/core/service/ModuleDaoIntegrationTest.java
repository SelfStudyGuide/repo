package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

@ContextConfiguration(locations = { "/test-config.ctx.xml",
		"/spring/core-service.ctx.xml" })
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
	public void verifyThatModuleIsCreate() {
		Module module = TstDataUtils.createModule();
		curriculumDao.saveModule(module);

		Collection<Module> modules = curriculumDao.getAllModules();
		Assert.assertThat(modules.size(), is(1));
		Module savedModule = modules.iterator().next();
		Assert.assertThat(savedModule.getName(), equalTo("name"));
		Assert.assertThat(savedModule.getDescription(), equalTo("desc"));
		Assert.assertThat(savedModule.getId(), is(not(0)));

	}

	@Test
	public void verifyThatModuleObjectCanBeFoundByItsId() {
		Module module = TstDataUtils.createModule();
		curriculumDao.saveModule(module);

		Module savedModule = getSavedModule();

		Module moduleFetchedById = curriculumDao.getModuleById(savedModule
				.getId());

		Assert.assertThat(moduleFetchedById.getName(), equalTo("name"));
		Assert.assertThat(moduleFetchedById.getDescription(), equalTo("desc"));
		Assert.assertThat(moduleFetchedById.getId(), is(not(0)));
	}

	@Test(expected = DataIntegrityViolationException.class)
	// @Ignore
	public void verifyThatExceptionIsThownWhenTwoModulesWithTheSameNameHasBeenCommitted() {
		Module module = TstDataUtils.createModule();
		curriculumDao.saveModule(module);

		Module module2 = TstDataUtils.createModule();
		curriculumDao.saveModule(module2);
	}

	@Test
	public void verifyThatModuleCanBeDeleted() {
		Module module = TstDataUtils.createModule();
		curriculumDao.saveModule(module);

		Module savedModule = getSavedModule();

		curriculumDao.deleteModule(savedModule.getId());

		Collection<Module> modules = curriculumDao.getAllModules();

		Assert.assertThat(modules.isEmpty(), is(true));
	}
	
	

}
