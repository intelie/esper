package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceStateListener;

import java.util.Set;

public class ConfiguratorContext {
    private final String engineURI;

    public ConfiguratorContext(String engineURI) {
        this.engineURI = engineURI;
    }

    public String getEngineURI() {
        return engineURI;
    }
}
