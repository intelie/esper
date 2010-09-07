package com.espertech.esper.client.hook;

public class ExceptionHandlerContext {
    private final String engineURI;
    private final Throwable throwable;
    private final String statementName;
    private final String epl;

    public ExceptionHandlerContext(String engineURI, Throwable throwable, String statementName, String epl) {
        this.engineURI = engineURI;
        this.throwable = throwable;
        this.statementName = statementName;
        this.epl = epl;
    }

    public String getEngineURI() {
        return engineURI;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getStatementName() {
        return statementName;
    }

    public String getEpl() {
        return epl;
    }
}
