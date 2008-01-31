package com.espertech.esper.eql.core;

import com.espertech.esper.eql.agg.AggregationSupport;
import com.espertech.esper.client.ConfigurationEngineDefaults;

import java.lang.reflect.Method;

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
