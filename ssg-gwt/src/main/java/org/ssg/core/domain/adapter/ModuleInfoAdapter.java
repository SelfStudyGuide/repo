package org.ssg.core.domain.adapter;

import org.springframework.beans.BeanUtils;
import org.ssg.core.domain.Module;
import org.ssg.core.dto.ModuleInfo;

public class ModuleInfoAdapter {
	private final Module module;

	public ModuleInfoAdapter(Module module) {
		this.module = module;
	}

	public void populate(ModuleInfo info) {
		BeanUtils.copyProperties(module, info);
		BeanUtils.copyProperties(this, info);
	}
}
