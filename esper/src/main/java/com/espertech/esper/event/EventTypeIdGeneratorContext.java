package com.espertech.esper.event;

public class EventTypeIdGeneratorContext {

    private final String engineURI;

    public EventTypeIdGeneratorContext(String engineURI) {
        this.engineURI = engineURI;
    }

    public String getEngineURI() {
        return engineURI;
    }
}
