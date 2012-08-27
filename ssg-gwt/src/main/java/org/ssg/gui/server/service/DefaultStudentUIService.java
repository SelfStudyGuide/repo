package org.ssg.gui.server.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiSecurityException;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.ActionHandlerLookup;
import org.ssg.gui.server.security.CommandPreAuthorize;
import org.ssg.gui.server.security.SsgSecurityException;

@Service
public class DefaultStudentUIService implements StudentUIService {
	private static final long serialVersionUID = -4678144429318786071L;

	private static final Log LOG = LogFactory.getLog(DefaultStudentUIService.class);

	@Autowired
	private ActionHandlerLookup handlerLookup;

	public <R extends Response> R execute(Action<R> action) throws SsgGuiServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Executing action: " + action.getActionName());
		}

		try {
			ActionHandler<R, Action<R>> handler = lookupCommandHandler(action);
			return doExecute(action, handler);
		} catch (SsgSecurityException e) {
			LOG.error("SecurityException - Action: " + action.getActionName() + " Message: " + e.getMessage());
			throw new SsgGuiSecurityException(e.getLocalizedMessage(), "security.accessdeny");
		} catch (SsgGuiServiceException e) {
			LOG.error("Fail to execute action: " + action.getActionName(), e);
			throw e;
		} catch (RuntimeException e) {
			LOG.error("Fail to execute action: " + action.getActionName(), e);
			throw new UnexpectedCommandException(e.getMessage(), "", action.getActionName());
		}

	}

	private <R extends Response> R doExecute(Action<R> action, ActionHandler<R, Action<R>> handler) {

		if (handler instanceof CommandPreAuthorize) {
			CommandPreAuthorize<Action<R>> handlerWithPreAuthorize = (CommandPreAuthorize<Action<R>>) handler;
			handlerWithPreAuthorize.preAuthorize(action);
		}

		return handler.execute(action);
	}

	private <R extends Response> ActionHandler<R, Action<R>> lookupCommandHandler(Action<R> action) {
		ActionHandler<R, Action<R>> handler = handlerLookup.findHandler(action.getClass());
		if (handler == null) {
			throw new UnexpectedCommandException("Action handler is not found", "", action.getActionName());
		}
		return handler;
	}

	public ActionHandlerLookup getHandlerLookup() {
		return handlerLookup;
	}

	public void setHandlerLookup(ActionHandlerLookup handlerLookup) {
		this.handlerLookup = handlerLookup;
	}

}
