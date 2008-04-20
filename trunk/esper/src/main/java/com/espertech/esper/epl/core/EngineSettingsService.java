package com.espertech.esper.epl.core;

import com.espertech.esper.client.ConfigurationEngineDefaults;

import java.net.URI;

/**
 * Service for engine-level settings around threading and concurrency.
 */
public class EngineSettingsService
{
    private ConfigurationEngineDefaults config;
    private URI[] plugInEventTypeResolutionURIs;

    /**
     * Ctor.
     * @param config is the configured defaults
     */
    public EngineSettingsService(ConfigurationEngineDefaults config, URI[] plugInEventTypeResolutionURIs)
    {
        this.config = config;
        this.plugInEventTypeResolutionURIs = plugInEventTypeResolutionURIs;
    }

    /**
     * Returns the settings.
     * @return engine settings
     */
    public ConfigurationEngineDefaults getEngineSettings()
    {
        return config;   
    }

    public URI[] getPlugInEventTypeResolutionURIs()
    {
        return plugInEventTypeResolutionURIs;
    }

    public void setPlugInEventTypeResolutionURIs(URI[] plugInEventTypeResolutionURIs)
    {
        this.plugInEventTypeResolutionURIs = plugInEventTypeResolutionURIs;
    }
}
