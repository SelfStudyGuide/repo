package org.ssg.gui.server.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public class DefaultActionHandlerLookup<R extends Response, A extends Action<R>> implements ActionHandlerLookup,
		ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;

	private Map<Class<A>, ActionHandler<R, A>> handlers = new HashMap<Class<A>, ActionHandler<R,A>>();

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		loadActionHandlers();
	}

	private void loadActionHandlers() {
		Map<String, ActionHandler> map = applicationContext.getBeansOfType(ActionHandler.class);
		for (ActionHandler<R, A> h : map.values()) {
			handlers.put(h.forClass(), h);
		}
	}

	public ActionHandler findHandler(Class actionClass) {
		return handlers.get(actionClass);
	}
}
