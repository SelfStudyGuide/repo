package org.ssg.core.domain.adapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.ssg.core.domain.Module;
import org.ssg.core.dto.ModuleInfo;
import org.ssg.core.support.TstDataUtils;

public class ModuleInfoAdapterTest {
	private Module module;

	@Before
	public void setUp() {
		module = TstDataUtils.enrichModuleWithId(TstDataUtils.createModule());
	}

	@Test
	public void verifyThatIdFieldIsCopied() {
		ModuleInfoAdapter adapter = new ModuleInfoAdapter(module);
		ModuleInfo info = new ModuleInfo();
		adapter.populate(info);

		assertThat(info.getId(), is(123));
	}

	@Test
	public void verifyThatNameFieldIsCopied() {
		ModuleInfoAdapter adapter = new ModuleInfoAdapter(module);
		ModuleInfo info = new ModuleInfo();
		adapter.populate(info);

		assertThat(info.getName(), equalTo("name"));
	}
}
