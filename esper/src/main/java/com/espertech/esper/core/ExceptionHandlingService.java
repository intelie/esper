package com.espertech.esper.core;

import com.espertech.esper.client.hook.ExceptionHandlerContext;
import com.espertech.esper.client.hook.ExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class ExceptionHandlingService {

    private static final Log log = LogFactory.getLog(ExceptionHandlingService.class);

    private final String engineURI;
    private final List<ExceptionHandler> handlers;

    public ExceptionHandlingService(String engineURI, List<ExceptionHandler> handlers) {
        this.engineURI = engineURI;
        this.handlers = handlers;
    }

    public void handleException(RuntimeException ex, EPStatementHandle handle) {
        if (handlers.isEmpty()) {
            log.error("Exception encountered processing statement '" + handle.getStatementName() + "' statement text '" + handle.getEPL() + "' : " + ex.getMessage(), ex);
            return;
        }

        ExceptionHandlerContext context = new ExceptionHandlerContext(engineURI, ex, handle.getStatementName(), handle.getEPL());
        for (ExceptionHandler handler : handlers) {
            handler.handle(context);
        }
    }
}
