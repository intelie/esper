package com.espertech.esper.support.client;

import com.espertech.esper.client.hook.ExceptionHandler;
import com.espertech.esper.client.hook.ExceptionHandlerContext;
import com.espertech.esper.client.hook.ExceptionHandlerFactory;
import com.espertech.esper.client.hook.ExceptionHandlerFactoryContext;

import java.util.ArrayList;
import java.util.List;

public class SupportExceptionHandlerFactoryRethrow implements ExceptionHandlerFactory {

    @Override
    public ExceptionHandler getHandler(ExceptionHandlerFactoryContext context) {
        return new SupportExceptionHandlerRethrow();
    }

    public static class SupportExceptionHandlerRethrow implements ExceptionHandler {
        public void handle(ExceptionHandlerContext context) {
            throw new RuntimeException("Unexpected exception in statement '" + context.getStatementName() +
                    "': " + context.getThrowable().getMessage(), context.getThrowable());
        }
    }
}
