package com.espertech.esper.epl.core;

import com.espertech.esper.client.ConfigurationEngineDefaults;

/**
 * Service for engine-level settings around threading and concurrency.
 */
public class EngineSettingsService
{
    private ConfigurationEngineDefaults config;

    /**
     * Ctor.
     * @param config is the configured defaults
     */
    public EngineSettingsService(ConfigurationEngineDefaults config)
    {
        this.config = config;
    }

    /**
     * Returns the settings.
     * @return engine settings
     */
    public ConfigurationEngineDefaults getEngineSettings()
    {
        return config;   
    }
}
