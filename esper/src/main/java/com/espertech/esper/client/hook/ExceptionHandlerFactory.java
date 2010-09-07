package com.espertech.esper.client.hook;

public interface ExceptionHandlerFactory {
    public ExceptionHandler getHandler(ExceptionHandlerFactoryContext content);
}
