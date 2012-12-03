package org.ssg.gui.server.command;

import org.ssg.gui.client.service.SsgGuiServiceException;

public class ActionHandlerUtils {

    public static void assertObjectNotNull(Object o, String errorCode, String errorMessage, Object... args)
            throws SsgGuiServiceException {
        if (o == null) {
            String message = String.format(errorMessage, args);
            throw new SsgGuiServiceException(message, errorCode);
        }
    }
}
