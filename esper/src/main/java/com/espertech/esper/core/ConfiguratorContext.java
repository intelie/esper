package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceStateListener;

import java.util.Map;
import java.util.Set;

public class ConfiguratorContext {
    private final String engineURI;
    private final Map<String, EPServiceProviderSPI> runtimes;

    public ConfiguratorContext(String engineURI, Map<String, EPServiceProviderSPI> runtimes) {
        this.engineURI = engineURI;
        this.runtimes = runtimes;
    }

    public String getEngineURI() {
        return engineURI;
    }

    public Map<String, EPServiceProviderSPI> getRuntimes() {
        return runtimes;
    }
}
