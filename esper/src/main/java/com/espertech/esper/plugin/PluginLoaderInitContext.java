package com.espertech.esper.plugin;

import com.espertech.esper.client.EPServiceProvider;

import java.util.Properties;

public class PluginLoaderInitContext
{
    private final String name;
    private final Properties properties;
    private final String configXml;
    private final EPServiceProvider epServiceProvider;

    /**
     * Initialization context for use with the adapter loader.
     * @param name is the loader name
     * @param properties is a set of properties from the configuration
     * @param epService is the SPI of the engine itself for sending events to
     */
    public PluginLoaderInitContext(String name, Properties properties, String configXml, EPServiceProvider epService)
    {
        this.name = name;
        this.properties = properties;
        this.configXml = configXml;
        this.epServiceProvider = epService;
    }

    public String getName()
    {
        return name;
    }

    public Properties getProperties()
    {
        return properties;
    }

    public String getConfigXml()
    {
        return configXml;
    }

    public EPServiceProvider getEpServiceProvider()
    {
        return epServiceProvider;
    }
}
