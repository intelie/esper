package com.espertech.esper.client.hook;

public class ExceptionHandlerFactoryContext {
    private final String engineURI;

    public ExceptionHandlerFactoryContext(String engineURI) {
        this.engineURI = engineURI;
    }

    public String getEngineURI() {
        return engineURI;
    }
}
